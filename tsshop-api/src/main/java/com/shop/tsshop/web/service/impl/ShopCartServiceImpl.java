package com.shop.tsshop.web.service.impl;

import com.shop.tsshop.config.exception.ApiCode;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.config.exception.SortException;
import com.shop.tsshop.web.model.domain.*;
import com.shop.tsshop.web.mapper.ShopCartMapper;
import com.shop.tsshop.web.model.dto.OrderDto;
import com.shop.tsshop.web.model.vo.CartListVo;
import com.shop.tsshop.web.model.vo.CartOrderVo;
import com.shop.tsshop.web.model.vo.OrderMoneyVo;
import com.shop.tsshop.web.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Service
public class ShopCartServiceImpl extends ServiceImpl<ShopCartMapper, ShopCart> implements ShopCartService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsSkuService skuService;
    @Autowired
    TransportManagerService transportManagerService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressOrderService addressOrderService;
    @Override
    public List<CartListVo> getList(Long  userId) {
        List<CartListVo> l=baseMapper.getList(userId);
        for (CartListVo cart : l){
            //0:正常，1：商品已下架，2，商品没库存，3，sku没库存
            if(cart.getGShelves()==1){
                cart.setStatus(1);
            }
            else if(cart.getGStocks()<=0){
                cart.setStatus(2);
            }
            else if(cart.getSStocks()!=null && cart.getSStocks()<=0){
                cart.setStatus(3);
            }
            else {
                cart.setStatus(0);
            }
        }
        return l;
    }

    @Override
    public Object addCart(ShopCart param) {
        Goods goods=goodsService.getById(param.getGoodsId());
        int num=param.getPurchaseQuantity();
        if(goods==null || goods.getDeleted()==-1){
            throw new MyException("商品不存在！");
        }
        int stocks=goods.getStockNumber();
        BigDecimal amt=goods.getPrice();
        if(param.getSkuId()!=null){
            GoodsSku sku=skuService.getById(param.getSkuId());
            if(sku==null || sku.getStocks()<=0 || sku.getDeleted()==1){
                throw new MyException("商品不存在或库存不足！");
            }
            stocks=sku.getStocks();
            if(num>sku.getStocks()){
                num=1;
            }
        }
        else {
            if(goods.getStockNumber()<=0){
                throw new MyException("库存不足！");
            }
        }
        if(num>stocks){
            num=1;
        }
        ShopCart cart=new ShopCart();
        cart.setUserId(param.getUserId());
        cart.setSkuId(param.getSkuId());
        cart.setPurchaseQuantity(num);
        cart.setGoodsId(param.getGoodsId());
        cart.setAmt(amt);
        cart.setTotalAmount(amt.multiply(new BigDecimal(num)));
        return baseMapper.insert(cart);
    }

    @Override
    public Object settle(List<Long> ids, Long userId, Long addressId) {
        CartOrderVo co=new CartOrderVo();
        if(ids.size()<=0){
            throw new MyException("购物车选择错误！");
        }
        List<CartListVo> l=baseMapper.getListByIds(ids,userId);
        BigDecimal freight=new BigDecimal("0");
        BigDecimal total=new BigDecimal("0");
        for (CartListVo cart : l){

            OrderDto dto=new OrderDto();
            dto.setUserId(userId);
            dto.setQuantity(cart.getPurchaseQuantity());
            dto.setAddressId(addressId);
            dto.setGoodsId(cart.getGoodsId());
            dto.setSkuId(cart.getSkuId());

            OrderMoneyVo vo= (OrderMoneyVo) orderService.allMoney(dto);

//            //0:正常，1：商品已下架，2，商品没库存，3，sku没库存
//            if(cart.getGShelves()==1){
//                throw new MyException("商品已下架！");
//            }
//            else if(cart.getGStocks()<=0){
//                throw new MyException("库存不足");
//            }
//            else if(cart.getSStocks()!=null && cart.getSStocks()<=0){
//                throw new MyException("库存不足");
//            }
//            if(cart.getPurchaseQuantity()<=0){
//                throw new MyException("数量错误");
//            }
            cart.setPrice(vo.getPrice());
            freight=freight .add(vo.getFreight());
            total=total.add(vo.getAmount());
        }

        co.setGoodsList(l);
        co.setFreight(freight);
        co.setTotal(total);
        co.setAllMoney(total.add(freight));
        return co;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object createOrder(List<Long> ids, Long userId, Long addressId,Integer isPass) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Address address = addressService.getById(addressId);
        //如果不为空就存放详细地址
        if (null == address) {
            throw new MyException("地址错误");
        }

        List<Long> orderIds=new ArrayList<>();
        List<CartListVo> l=baseMapper.getListByIds(ids,userId);
        for (CartListVo cart : l){

            OrderDto dto=new OrderDto();
            dto.setUserId(userId);
            dto.setQuantity(cart.getPurchaseQuantity());
            dto.setAddressId(addressId);
            dto.setGoodsId(cart.getGoodsId());
            dto.setSkuId(cart.getSkuId());

            //调用创建订单函数
            try {
                Long id= (Long) orderService.createOrder(dto);
                baseMapper.deleteById(cart.getId());
                orderIds.add(id);
            }catch (SortException e){
                //如果其中一个库存不足，且前端未发来确认参数，返回特殊报错
                if(isPass==0)throw new MyException(ApiCode.OUT_OF_STOCK);
                break;
            }


//            //0:正常，1：商品已下架，2，商品没库存，3，sku没库存
//            int num=cart.getPurchaseQuantity();
//            if(cart.getGShelves()==1){
//                throw new MyException("商品已下架！");
//            }
//
//            if(cart.getPurchaseQuantity()<=0){
//                throw new MyException("购物车中有商品数量错误");
//            }
//            int k=0;
//            if(cart.getSkuId()==null){
//                k=goodsService.updateStock(num, cart.getGoodsId());
//            }
//            else {
//                k=skuService.updateStock(num,cart.getSkuId());
//            }
//            if(k==0){
//                if(isPass==0)throw new MyException(ApiCode.OUT_OF_STOCK);
//                break;
//            }
//            Order order = new Order();
//            order.setOrderNo(sdf.format(new Date()) + (int) ((Math.random() * 9 + 1) * 1000));
//
//            AddressOrder addressOrder=new AddressOrder();
//            BeanUtils.copyProperties(address,addressOrder);
//            addressOrder.setOrderNo(order.getOrderNo());
//            addressOrderService.save(addressOrder);
//            order.setAddressId(addressOrder.getId());
//
//            order.setOrderStatus(0);
//            order.setDeleted(0);
//            order.setGoodsId(cart.getGoodsId());
//            order.setSkuId(cart.getSkuId());
//
//            BigDecimal price = cart.getPrice();
//            BigDecimal freight=transportManagerService.calculateTransfee(price, cart.getPurchaseQuantity(), cart.getDeliveryTemplateId(),address.getArea());
//            BigDecimal total=cart.getPrice().multiply(new BigDecimal(cart.getPurchaseQuantity()));
//
//            order.setPrice(price);
//            order.setTotalAmount(total.add(freight));
//            order.setUserId(userId);
//            order.setFreight(freight);
//            //购买数量
//            order.setQuantity(cart.getPurchaseQuantity());
//            order.setCreateTime(new Date());
//            Boolean kk=orderService.save(order);
//            if(kk){
//                baseMapper.deleteById(cart.getId());
//            }
//            orderIds.add(order.getId());
        }
        if(orderIds.size()<=0){
            throw new MyException("库存不足");
        }
        return orderIds;
    }
}

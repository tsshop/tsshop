package com.shop.tsshop.web.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.config.exception.SortException;
import com.shop.tsshop.web.enums.NumberEnmus;
import com.shop.tsshop.web.mapper.LiveGiftMapper;
import com.shop.tsshop.web.model.domain.*;
import com.shop.tsshop.web.mapper.OrderMapper;
import com.shop.tsshop.web.model.dto.OrderDto;
import com.shop.tsshop.web.model.dto.OrderListDto;
import com.shop.tsshop.web.model.vo.OrderDetailVo;
import com.shop.tsshop.web.model.vo.OrderMoneyVo;
import com.shop.tsshop.web.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsSkuService skuService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressOrderService addressOrderService;
    @Autowired
    TransportManagerService transportManagerService;
    @Autowired
    AreaService areaService;

    @Autowired
    SeckillService seckillService;
    @Autowired
    SeckillGoodsService seckillGoodsService;

    @Resource
    LiveGiftMapper liveGiftMapper;

    @Autowired
    private LiveGoodsSkuService liveGoodsSkuService;

    @Autowired
    private LiveGoodsService liveGoodsService;


    @Autowired
    private LiveStoreService liveStoreService;

    @Autowired
    private LiveStoreIncomeLogService liveStoreIncomeLogService;

    @Autowired
    private LiveBroadcastService liveBroadcastService;

    @Override
    public Object createOrder(OrderDto orderDto) {
        Long goodsId = orderDto.getGoodsId();
        Goods goods = goodsService.getById(goodsId);
        if(goods==null || goods.getDeleted()==1 || goods.getShelves()==1){
            throw new MyException("商品不存在");
        }
        Long skuId = orderDto.getSkuId();
        GoodsSku sku=new GoodsSku();
        if(skuId !=null){
            sku=skuService.getById(skuId);
            if(sku==null || sku.getDeleted()==1){
                throw new MyException("商品不存在");
            }
        }
        Address address=addressService.getById(orderDto.getAddressId());
        if(address==null || !Objects.equals(address.getUserId(), orderDto.getUserId()) || address.getDeleted()==1){
            throw new MyException("地址错误");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNo=sdf.format(new Date()) + (int) ((Math.random() * 9 + 1) * 1000);
        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setOrderNo(orderNo);
        //地址处理
        AddressOrder addressOrder=new AddressOrder();
        BeanUtils.copyProperties(address,addressOrder);
        addressOrder.setOrderNo(orderNo);
        addressOrderService.save(addressOrder);
        order.setAddressId(addressOrder.getId());
        order.setGoodsId(goodsId);

        //价格计算
        int num=orderDto.getQuantity();
        BigDecimal total=new BigDecimal("0");
        BigDecimal price=new BigDecimal("0");
        BigDecimal freight=new BigDecimal("0");
        SeckillGoods seckillGoods=null;
        int k=0;
        if(skuId!=null){
            k=skuService.updateStock(num,skuId);
            order.setSkuId(skuId);
            price=sku.getPrice();
            seckillGoods=seckillGoodsService.getGoodsById(skuId,1);
        }
        else {
            k=goodsService.updateStock(num, goodsId);
            price=goods.getPrice();
            seckillGoods=seckillGoodsService.getGoodsById(goodsId,2);
        }
        if(k!=1){
            throw new SortException("库存不足！");
        }
        //如果商品在，秒杀活动中，
        if(seckillGoods!=null){
            Seckill seckill=seckillService.getOne(new LambdaQueryWrapper<Seckill>().eq(Seckill::getStatus,1));
            Date date= new Date(Math.max(new Date().getTime(),seckill.getStartTime().getTime()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.SECOND,0); //这是将当天的【秒】设置为0
            calendar.set(Calendar.MINUTE,0); //这是将当天的【分】设置为0
            calendar.set(Calendar.HOUR_OF_DAY,seckillGoods.getTime()); //这是将当天的【时】设置为0
            date=calendar.getTime();
            if((new Date()).getTime()>date.getTime()){
                k=seckillGoodsService.updateStock(num, seckillGoods.getId());
                //如果秒杀库存还有，享受秒杀价
                if(k==1){
                    price=seckillGoods.getOffer();
                }
            }
        }

        //计算总价
        if(ObjectUtils.isNotEmpty(goods.getDeliveryTemplateId())){
            freight=transportManagerService.calculateTransfee(price, num, goods.getDeliveryTemplateId(), address.getArea());
        }
        total=price.multiply(new BigDecimal(num)).add(freight);
        order.setFreight(freight);//运费
        order.setQuantity(num);//数量
        order.setPrice(price);//单价
        order.setTotalAmount(total);

        order.setOrderStatus(0);//待付款
        order.setDeleted(0);

        save(order);

        return order.getId();
    }

    @Override
    public Object allMoney(OrderDto orderDto) {
        OrderMoneyVo moneyVo=new OrderMoneyVo();

        Long goodsId = orderDto.getGoodsId();
        Goods goods = goodsService.getById(goodsId);
        if(goods==null || goods.getDeleted()==1 || goods.getShelves()==1){
            throw new MyException("商品不存在");
        }
        int num=orderDto.getQuantity();
        BigDecimal total=new BigDecimal("0");
        BigDecimal price=goods.getPrice();
        BigDecimal freight=new BigDecimal("0");

        Long skuId = orderDto.getSkuId();
        GoodsSku sku=new GoodsSku();
        SeckillGoods seckillGoods=null;
        if(skuId !=null){
            sku=skuService.getById(skuId);
            if(sku==null || sku.getDeleted()==1){
                throw new MyException("商品不存在");
            }
            price=sku.getPrice();
            seckillGoods=seckillGoodsService.getGoodsById(skuId,1);
        }
        else {
            seckillGoods=seckillGoodsService.getGoodsById(skuId,2);
        }
        //如果商品在，秒杀活动中，
        if(seckillGoods!=null){
            Seckill seckill=seckillService.getOne(new LambdaQueryWrapper<Seckill>().eq(Seckill::getStatus,1));
            Date date= new Date(Math.max(new Date().getTime(),seckill.getStartTime().getTime()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.SECOND,0); //这是将当天的【秒】设置为0
            calendar.set(Calendar.MINUTE,0); //这是将当天的【分】设置为0
            calendar.set(Calendar.HOUR_OF_DAY,seckillGoods.getTime()); //这是将当天的【时】设置为0
            date=calendar.getTime();
            if((new Date()).getTime()>date.getTime()){
                price=seckillGoods.getOffer();
            }
        }

        Address address=addressService.getById(orderDto.getAddressId());
        if(address==null || !Objects.equals(address.getUserId(), orderDto.getUserId()) || address.getDeleted()==1){
//            throw new MyException("地址错误");
        }
        else {
            freight=transportManagerService.calculateTransfee(price, num, goods.getDeliveryTemplateId(), address.getArea());
        }
        BigDecimal amount=price.multiply(new BigDecimal(num));
        total=amount.add(freight);
        moneyVo.setFreight(freight);//运费
        moneyVo.setQuantity(num);//数量
        moneyVo.setPrice(price);//单价
        moneyVo.setAmount(amount);//商品总价
        moneyVo.setTotalAmount(total);

        return moneyVo;
    }

    @Override
    public OrderDetailVo getDetail(Order order) {
        OrderDetailVo detailVo=new OrderDetailVo();
        BeanUtils.copyProperties(order,detailVo);
        Goods goods = goodsService.getById(order.getGoodsId());
        detailVo.setGoodsName(goods.getName());
        detailVo.setSrc(goods.getHeadPortrait());
        if(ObjectUtils.isNotEmpty(order.getSkuId())){
            GoodsSku sku=skuService.getById(order.getSkuId());
            detailVo.setSrc(sku.getPic());
            detailVo.setSku(sku.getProperties());
        }
        AddressOrder address=addressOrderService.getById(order.getAddressId());
        Area pr = areaService.getById(address.getProvince());
        Area ci = areaService.getById(address.getCity());
        Area ar = areaService.getById(address.getArea());
        address.setAreaName(ar.getAreaName());
        address.setProvinceName(pr.getAreaName());
        address.setCityName(ci.getAreaName());
        detailVo.setAddressOrder(address);
        return detailVo;
    }

    @Override
    public List<OrderDetailVo> getListByStatus(OrderListDto dto) {
        return baseMapper.getListByStatus(dto);
    }

    @Override
    public void cancel(Order order) {
        Order o=new Order();
        o.setId(order.getId());
        o.setOrderStatus(-1);
        updateById(o);
        Integer num = order.getQuantity();
        if (ObjectUtils.isNotEmpty(order.getSkuId())) {
            skuService.updateStock(-num,order.getSkuId());
        } else {
            goodsService.updateStock(-num, order.getGoodsId());
        }
    }

    @Override
    public Object createGiftOrder(OrderDto dto) {
        // 礼物订单
        LiveGift liveGift = liveGiftMapper.selectOne(new LambdaQueryWrapper<LiveGift>().eq(LiveGift::getId, dto.getGoodsId()).eq(LiveGift::getDeleted, false).eq(LiveGift::getShelves,false));
        if (ObjectUtil.isNull(liveGift)){
            throw new MyException("请刷新重试");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNo=sdf.format(new Date()) + (int) ((Math.random() * 9 + 1) * 1000);
        Order order = new Order();
        order.setUserId(dto.getUserId());
        order.setOrderNo(orderNo);
        order.setGoodsId(dto.getGoodsId());
        order.setQuantity(dto.getQuantity());
        order.setPrice(liveGift.getUnitPrice());
        order.setTotalAmount(liveGift.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity())).setScale(2,BigDecimal.ROUND_DOWN));
        order.setOrderType(dto.getOrderType());
        order.setOrderStatus(NumberEnmus.ZERO.getNumber());
        order.setDeleted(NumberEnmus.ZERO.getNumber());
        save(order);
        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object createLiveOrder(OrderDto orderDto) {
        Long goodsId = orderDto.getGoodsId();
        Goods goods = goodsService.getById(goodsId);
        if(goods==null || goods.getDeleted()==1 || goods.getShelves()==1){
            throw new MyException("商品不存在");
        }
        Long skuId = orderDto.getSkuId();

        LiveGoodsSku liveGoodsSku = liveGoodsSkuService.getOne(new LambdaQueryWrapper<LiveGoodsSku>()
                .eq(LiveGoodsSku::getSkuId,skuId)
                .eq(LiveGoodsSku::getLiveId,orderDto.getLiveId()));

        if (ObjectUtil.isEmpty(liveGoodsSku)){
            throw new MyException("商品不存在");
        }
        GoodsSku sku=skuService.getById(skuId);
        if (ObjectUtil.isEmpty(sku)){
            throw new MyException("商品不存在");
        }

        Address address=addressService.getById(orderDto.getAddressId());
        if(address==null || !Objects.equals(address.getUserId(), orderDto.getUserId()) || address.getDeleted()==1){
            throw new MyException("地址错误");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNo=sdf.format(new Date()) + (int) ((Math.random() * 9 + 1) * 1000);
        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setOrderNo(orderNo);
        order.setLiveStoreId(orderDto.getLiveStoreId());
        order.setOrderType(orderDto.getOrderType());

        //地址处理
        AddressOrder addressOrder=new AddressOrder();
        BeanUtils.copyProperties(address,addressOrder);
        addressOrder.setOrderNo(orderNo);
        addressOrderService.save(addressOrder);
        order.setAddressId(addressOrder.getId());
        order.setGoodsId(goodsId);

        //价格计算
        int num=orderDto.getQuantity();
        BigDecimal total;
        BigDecimal price;
        BigDecimal freight=new BigDecimal("0");

        if (skuService.updateStock(num,skuId) < 1){
            throw new SortException("库存不足！");
        }

        if (liveGoodsSkuService.updateStock(num,liveGoodsSku.getId()) < 1){
            throw new SortException("库存不足！");
        }

        order.setSkuId(skuId);
        price=liveGoodsSku.getPrice();

        //计算总价
        if(ObjectUtils.isNotEmpty(goods.getDeliveryTemplateId())){
            freight=transportManagerService.calculateTransfee(price, num, goods.getDeliveryTemplateId(), address.getArea());
        }
        total=price.multiply(new BigDecimal(num)).add(freight);
        order.setFreight(freight);//运费
        order.setQuantity(num);//数量
        order.setPrice(price);//单价
        order.setTotalAmount(total);
        order.setLiveId(orderDto.getLiveId());
        order.setOrderStatus(0);//待付款
        order.setDeleted(0);

        save(order);
        return order.getId();
    }

    @Override
    public Object liveAllMoney(OrderDto orderDto) {
        OrderMoneyVo moneyVo=new OrderMoneyVo();

        Long goodsId = orderDto.getGoodsId();
        Goods goods = goodsService.getById(goodsId);
        if(goods==null || goods.getDeleted()==1 || goods.getShelves()==1){
            throw new MyException("商品不存在");
        }
        int num=orderDto.getQuantity();
        BigDecimal total;
        BigDecimal price;
        BigDecimal freight=new BigDecimal("0");

        Long skuId = orderDto.getSkuId();

        LiveGoodsSku liveGoodsSku = liveGoodsSkuService.getOne(new LambdaQueryWrapper<LiveGoodsSku>()
                .eq(LiveGoodsSku::getSkuId,skuId)
                .eq(LiveGoodsSku::getLiveId,orderDto.getLiveId()));

        if (ObjectUtil.isEmpty(liveGoodsSku)){
            throw new MyException("商品不存在");
        }

        price=liveGoodsSku.getPrice();


        Address address=addressService.getById(orderDto.getAddressId());
        if(address==null || !Objects.equals(address.getUserId(), orderDto.getUserId()) || address.getDeleted()==1){
//            throw new MyException("地址错误");
        }
        else {
            freight=transportManagerService.calculateTransfee(price, num, goods.getDeliveryTemplateId(), address.getArea());
        }
        BigDecimal amount=price.multiply(new BigDecimal(num));
        total=amount.add(freight);
        moneyVo.setFreight(freight);//运费
        moneyVo.setQuantity(num);//数量
        moneyVo.setPrice(price);//单价
        moneyVo.setAmount(amount);//商品总价
        moneyVo.setTotalAmount(total);

        return moneyVo;
    }

    @Override
    public void liveOrderProfit(Order order) {
        if (!Objects.equals(order.getOrderType(), NumberEnmus.THREE.getNumber())){
            return;
        }
        GoodsSku goodsSku = skuService.getById(order.getSkuId());
        BigDecimal costPrice = goodsSku.getCostPrice();
        BigDecimal profit = new BigDecimal(order.getQuantity()).multiply(order.getPrice().subtract(costPrice));

        liveStoreService.addProfit(order.getLiveStoreId(),profit);
        liveBroadcastService.addProfit(order.getLiveId(),profit);
        liveStoreIncomeLogService.create(order.getLiveStoreId(),profit,1);
        liveGoodsService.addSalesVolume(order.getQuantity(),order.getLiveId(),order.getGoodsId());
    }
}

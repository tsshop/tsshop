package com.shop.tsshop.web.controller;


import com.github.pagehelper.PageHelper;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.exception.ApiCode;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.config.express.ExpressService;
import com.shop.tsshop.config.express.config.ExpressProperties;
import com.shop.tsshop.config.express.dao.ExpressDTO;
import com.shop.tsshop.web.enums.NumberEnmus;
import com.shop.tsshop.web.model.domain.Order;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.OrderDto;
import com.shop.tsshop.web.model.dto.OrderExpressDTO;
import com.shop.tsshop.web.model.dto.OrderListDto;
import com.shop.tsshop.web.model.vo.OrderDetailVo;
import com.shop.tsshop.web.service.GoodsService;
import com.shop.tsshop.web.service.OrderService;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.util.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsService goodsService;

    @Value("${express.ReqURL}")
    private String reqURL;
    @Value("${express.enable}")
    private boolean enable;
    @Value("${express.appId}")
    private String expressAppId;
    @Value("${express.appKey}")
    private String expressAppKey;


    @PostMapping("/create")
    //"用户下单")
    public ApiResult<Object> createOrder(@RequestBody OrderDto orderDto, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        if(user==null || 1==user.getDeleted()){
            return ApiResult.fail("用户不存在");
        }
        if (0 >= orderDto.getQuantity()) {
            return ApiResult.fail("数量错误");
        }
        String ip = IpUtils.getIpAddr(request);
        orderDto.setIp(ip);
        orderDto.setUserId(user.getId());
        if (Objects.equals(orderDto.getOrderType(), NumberEnmus.ONE.getNumber())){
            return ApiResult.ok(orderService.createGiftOrder(orderDto));
        }
        if (Objects.equals(orderDto.getOrderType(), NumberEnmus.THREE.getNumber())){
            return ApiResult.ok(orderService.createLiveOrder(orderDto));
        }
        return ApiResult.ok(orderService.createOrder(orderDto));
    }

    @PostMapping("/allMoney")
    //"合计金额")
    public ApiResult<Object> allMoney(@RequestBody OrderDto orderDto, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        orderDto.setUserId(user.getId());
        if (Objects.equals(orderDto.getOrderType(), NumberEnmus.ZERO.getNumber())){
            return ApiResult.ok(orderService.allMoney(orderDto));
        }
        if (Objects.equals(orderDto.getOrderType(), NumberEnmus.THREE.getNumber())){
            return ApiResult.ok(orderService.liveAllMoney(orderDto));
        }
        return ApiResult.fail("订单类型错误");
    }

    @GetMapping("/detail")
    //"用户查看订单详情")
    public ApiResult<Object> detail(Long orderId, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        // 获得图片url
        Order order = orderService.getById(orderId);
        if (null == order || !Objects.equals(order.getUserId(), user.getId())) {
            return ApiResult.fail("订单不存在！");
        }
        OrderDetailVo orderDetailDto = orderService.getDetail(order);
        return ApiResult.ok(orderDetailDto);
    }


    @PostMapping("getOrderList")
    //"用户订单列表")
    public ApiResult<Object> orderList(@RequestBody OrderListDto dto, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        dto.setUserId(user.getId());
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize(),"o.id desc");
        List<OrderDetailVo> map = orderService.getListByStatus(dto);
        return ApiResult.ok(map);
    }

    @PostMapping("harvest")
    //"确定收货")
    public ApiResult harvest(@RequestBody OrderDto orderDto, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        Order order = orderService.getById(orderDto.getOrderId());
        if(!Objects.equals(user.getId(), order.getUserId()) || !(order.getOrderStatus()==2 || order.getOrderStatus()==3)){
            throw new MyException("订单状态错误");
        }
        Order oUpdate=new Order();
        oUpdate.setOrderStatus(3);
        oUpdate.setId(order.getId());
        orderService.updateById(oUpdate);
        return ApiResult.ok();
    }


    @PostMapping("/cancel")
    //"用户取消订单")
    public ApiResult<Object> cancelOrder(@RequestBody OrderDto orderDto, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        // 查询订单id
        Order order = orderService.getById(orderDto.getOrderId());
        Integer orderStatus = order.getOrderStatus();
        if (order.getUserId().compareTo(user.getId()) != 0) {
            return ApiResult.fail("当前订单不是您的订单 不能取消!");
        }
        if (orderStatus !=0) {
            return ApiResult.fail("状态错误,无法取消订单！");
        }
        orderService.cancel(order);

        return ApiResult.ok(ApiCode.SUCCESS);
    }


    /**
     * 查询物流
     */
    @PostMapping("/expressInfo")
    public ApiResult<ExpressDTO> expressInfo(@RequestBody OrderExpressDTO order) {
        ExpressService expressService = new ExpressService(reqURL);
        ExpressProperties expressProperties = new ExpressProperties();
        expressProperties.setAppId(expressAppId);
        expressProperties.setEnable(enable);
        expressProperties.setAppKey(expressAppKey);
        expressService.setProperties(expressProperties);
        ExpressDTO expressDTO = expressService.getExpressInfo(/*"2582771510808879100"*/ order.getOrderNo(), /*"ZTO"*/order.getExpress(), /*"75871594622226"*/ order.getExpressNo());
        expressDTO.setOrderStatus(order.getOrderNo());
        return ApiResult.ok(expressDTO);
    }

}

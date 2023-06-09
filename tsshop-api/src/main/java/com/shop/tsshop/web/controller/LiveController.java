package com.shop.tsshop.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.enums.LiveEnums;
import com.shop.tsshop.web.model.domain.*;
import com.shop.tsshop.web.netty.protocol.command.MsgCommand;
import com.shop.tsshop.web.netty.utils.PushUtils;
import com.shop.tsshop.web.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author : tsshop
 * @date : 2022/12/19
 */
@Slf4j
@RestController
@RequestMapping("/live")
public class LiveController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private LiveBroadcastService liveBroadcastService;

    @Autowired
    private LiveLinkService liveLinkService;

    @Autowired
    private LiveGoodsService liveGoodsService;

    @Autowired
    private LiveGoodsSkuService liveGoodsSkuService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LiveStoreService liveStoreService;

    @Autowired
    private OrderService orderService;

    /**
     * 创建直播
     * @param liveBroadcast
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/createLive")
    public ApiResult createLive(@RequestBody LiveBroadcast liveBroadcast, HttpServletRequest httpServletRequest) {
        return ApiResult.ok(liveBroadcastService.createLive(liveBroadcast,httpServletRequest));
    }

    /**
     * 修改直播
     * @param liveBroadcast
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/updateLive")
    public ApiResult updateLive(@RequestBody LiveBroadcast liveBroadcast, HttpServletRequest httpServletRequest) {
        User user = redisService.getCurrentUser(httpServletRequest);
        LiveBroadcast oldLive = liveBroadcastService.getById(liveBroadcast.getId());
        if (ObjectUtil.isEmpty(oldLive) || !LiveEnums.LiveStatus.NOT_STARTED.getCode().equals(oldLive.getStatus())){
            return ApiResult.fail("直播不存在或已结束");
        }
        if(!oldLive.getUserId().equals(user.getId())){
            return ApiResult.fail("无权限");
        }
        liveBroadcastService.updateById(liveBroadcast);
        return ApiResult.ok();
    }

    /**
     * 我的预约直播
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/reserveLive")
    public ApiResult reserveLive(HttpServletRequest httpServletRequest) {
        User user = redisService.getCurrentUser(httpServletRequest);
        LambdaQueryWrapper<LiveBroadcast> wrapper = new LambdaQueryWrapper<LiveBroadcast>()
                .eq(LiveBroadcast::getUserId,user.getId())
                .eq(LiveBroadcast::getStatus,LiveEnums.LiveStatus.NOT_STARTED.getCode())
                .gt(LiveBroadcast::getReckonTime, DateUtil.offsetMinute(new Date(), -10));
        LiveBroadcast liveBroadcast = liveBroadcastService.getOne(wrapper);
        if (ObjectUtil.isEmpty(liveBroadcast)){
            return ApiResult.ok();
        }
        liveBroadcast.setUserName(user.getName());
        liveBroadcast.setUserAvatarUrl(user.getAvatarUrl());
        List<LiveGoods> liveGoodsList = liveGoodsService.list(new LambdaQueryWrapper<LiveGoods>()
                .eq(LiveGoods::getLiveId, liveBroadcast.getId()));
        if (CollectionUtil.isNotEmpty(liveGoodsList)){
            liveGoodsList.forEach(liveGoods -> {
                Goods goods = goodsService.getById(liveGoods.getGoodsId());
                liveGoods.setName(goods.getName());
                liveGoods.setHeadPortrait(goods.getHeadPortrait());
                liveGoods.setDetail(goods.getDetail());
                liveGoods.setPrice(goods.getPrice());
                List<LiveGoodsSku> liveGoodsSkuList = liveGoodsSkuService.list(new LambdaQueryWrapper<LiveGoodsSku>()
                        .eq(LiveGoodsSku::getLiveGoodsId, liveGoods.getId()));
                liveGoodsSkuList.forEach(goodsSku -> {
                    GoodsSku sku = goodsSkuService.getById(goodsSku.getSkuId());
                    goodsSku.setProperties(sku.getProperties())
                            .setPic(sku.getPic())
                            .setSkuName(sku.getSkuName())
                            .setProdName(sku.getProdName())
                            .setGoodsId(sku.getGoodsId());
                    if (sku.getStocks() < goodsSku.getStocks()){
                        goodsSku.setStocks(sku.getStocks());
                    }
                });
                liveGoods.setGoodsSkuList(liveGoodsSkuList);
            });
        }
        liveBroadcast.setGoodsList(liveGoodsList);
        return ApiResult.ok(liveBroadcast);
    }

    /**
     * 连麦列表
     * @param liveId
     * @return
     */
    @GetMapping("/linkList")
    public ApiResult linkList(Long liveId){
        List<LiveLink> list = liveLinkService.list(new LambdaQueryWrapper<LiveLink>()
                .eq(LiveLink::getLiveId, liveId)
                .eq(LiveLink::getStatus,LiveEnums.LiveLinkStatus.ONE.getCode())
                .eq(LiveLink::getIsFromUser, true)
                .orderByAsc(LiveLink::getCreateTime));
        list.forEach(item -> {
            User user = userService.getById(item.getUserId());
            item.setUserName(user.getName());
            item.setUserAvatarUrl(user.getAvatarUrl());
        });
        return ApiResult.ok(list);
    }

    /**
     * 连麦
     * @param liveLink
     * @param request
     * @return
     */
    @PostMapping("/link")
    public ApiResult link(@RequestBody LiveLink liveLink, HttpServletRequest request){
        liveBroadcastService.link(liveLink,request);
        return ApiResult.ok();
    }

    /**
     * 申请连麦
     * @param liveLink
     * @param request
     * @return
     */
    @PostMapping("/createLink")
    public ApiResult createLink(@RequestBody LiveLink liveLink, HttpServletRequest request){

        liveBroadcastService.createLink(liveLink,request);
        return ApiResult.ok(liveLink);
    }

    /**
     * 在线用户
     * @param roomId
     * @return
     */
    @GetMapping("/iveOnlineUser")
    public ApiResult iveOnlineUser(Long roomId){
        String onlineKey = "live_online_user:" + roomId;
        Map map = redisTemplate.opsForHash().entries(onlineKey);
        return ApiResult.ok(map);
    }

    /**
     * 我的直播记录
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/myLiveLogList")
    public ApiResult myLiveLogList(Pageable pageable,String year,String month,HttpServletRequest httpServletRequest){
        Long userId = redisService.getCurrentUser(httpServletRequest).getId();
        LambdaQueryWrapper<LiveBroadcast> wrapper = new LambdaQueryWrapper<LiveBroadcast>()
                .eq(LiveBroadcast::getUserId,userId)
                .eq(LiveBroadcast::getStatus, LiveEnums.LiveStatus.END_OF_LIVE.getCode())
                .orderByDesc(LiveBroadcast::getStartTime);
        if ("0".equals(month)){
            DateTime beginOfYear = DateUtil.beginOfYear(DateUtil.parse(year, "yyyy"));
            DateTime endOfYear = DateUtil.endOfYear(DateUtil.parse(year, "yyyy"));
            wrapper.between(LiveBroadcast::getStartTime,beginOfYear,endOfYear);
        }else {
            DateTime monthStartTime = DateUtil.beginOfMonth(DateUtil.parse(year + "-" + month, "yyyy-MM"));
            log.info("monthStartTime[{}]",monthStartTime);
            DateTime monthEndTime = DateUtil.endOfMonth(DateUtil.parse(year + "-" + month, "yyyy-MM"));
            log.info("monthEndTime[{}]",monthEndTime);
            wrapper.between(LiveBroadcast::getStartTime,monthStartTime,monthEndTime);
        }
        IPage<LiveBroadcast> page = liveBroadcastService.pageList(pageable,wrapper);
        page.getRecords().forEach(liveBroadcast -> {
            Long between = DateUtil.between(liveBroadcast.getStartTime(), liveBroadcast.getEndTime(), DateUnit.MS);
            liveBroadcast.setBetween(DateUtil.formatBetween(between, BetweenFormater.Level.SECOND));
        });
        return ApiResult.ok(page);
    }

    /**
     * 我的直播统计
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/myLiveLogStatistics")
    public ApiResult myLiveLogStatistics(String year,String month,HttpServletRequest httpServletRequest){
        Long userId = redisService.getCurrentUser(httpServletRequest).getId();
        LiveStore liveStore = liveStoreService.getOne(new LambdaQueryWrapper<LiveStore>()
                .eq(LiveStore::getUserId, userId));
        if (ObjectUtil.isEmpty(liveStore)){
            return ApiResult.ok();
        }
        LambdaQueryWrapper<LiveBroadcast> LiveBroadcastWrapper = new LambdaQueryWrapper<LiveBroadcast>()
                .eq(LiveBroadcast::getUserId,userId)
                .eq(LiveBroadcast::getStatus, LiveEnums.LiveStatus.END_OF_LIVE.getCode());

        LambdaQueryWrapper<Order> orderWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getLiveStoreId,liveStore.getId())
                .eq(Order::getOrderType,3)
                .gt(Order::getOrderStatus,1);

        if ("0".equals(month)){
            DateTime beginOfYear = DateUtil.beginOfYear(DateUtil.parse(year, "yyyy"));
            DateTime endOfYear = DateUtil.endOfYear(DateUtil.parse(year, "yyyy"));
            LiveBroadcastWrapper.between(LiveBroadcast::getStartTime,beginOfYear,endOfYear);
            orderWrapper.between(Order::getCreateTime,beginOfYear,endOfYear);
        }else {
            DateTime monthStartTime = DateUtil.beginOfMonth(DateUtil.parse(year + "-" + month, "yyyy-MM"));
            log.info("monthStartTime[{}]",monthStartTime);
            DateTime monthEndTime = DateUtil.endOfMonth(DateUtil.parse(year + "-" + month, "yyyy-MM"));
            log.info("monthEndTime[{}]",monthEndTime);
            LiveBroadcastWrapper.between(LiveBroadcast::getStartTime,monthStartTime,monthEndTime);
            orderWrapper.between(Order::getCreateTime,monthStartTime,monthEndTime);
        }
        int sessionCount = liveBroadcastService.count(LiveBroadcastWrapper);
        int orderCount = orderService.count(orderWrapper);
        Map result = new HashMap();
        result.put("sessionCount",sessionCount);
        result.put("orderCount",orderCount);
        return ApiResult.ok(result);
    }

    /**
     * 查询直播列表
     * @return
     */
    @GetMapping("/onLiveList")
    @PassToken
    public ApiResult onLiveList(Pageable pageable,Integer status,HttpServletRequest request){

        LambdaQueryWrapper<LiveBroadcast> wrapper = new LambdaQueryWrapper<>();
        if (LiveEnums.LiveStatus.LIVE_BROADCAST.getCode().equals(status)){
            wrapper.eq(LiveBroadcast::getStatus,
                            LiveEnums.LiveStatus.LIVE_BROADCAST.getCode());
        }else  if (LiveEnums.LiveStatus.NOT_STARTED.getCode().equals(status)){
            wrapper.eq(LiveBroadcast::getStatus,LiveEnums.LiveStatus.NOT_STARTED.getCode())
                    .gt(LiveBroadcast::getReckonTime, DateUtil.offsetMinute(new Date(), -10));
        } else {
            wrapper.eq(LiveBroadcast::getStatus,
                    LiveEnums.LiveStatus.LIVE_BROADCAST.getCode())
                    .eq(LiveBroadcast::getStatus,
                            LiveEnums.LiveStatus.LIVE_BROADCAST.getCode())
                    .or()
                    .nested(w -> {
                        w.eq(LiveBroadcast::getStatus,LiveEnums.LiveStatus.NOT_STARTED.getCode())
                                .gt(LiveBroadcast::getReckonTime, DateUtil.offsetMinute(new Date(), -10));
                    });
        }
        IPage<LiveBroadcast> page = liveBroadcastService.pageList(pageable,wrapper);
        page.getRecords().forEach(item -> {
            LiveStore liveStore = liveStoreService.getById(item.getLiveStoreId());
            //设置直播人信息
            User userInfo = userService.getById(item.getUserId());
            item.setUserName(userInfo.getName());
            item.setLiveStoreName(liveStore.getStoreName());
            if (LiveEnums.LiveStatus.LIVE_BROADCAST.getCode().equals(item.getStatus())){
                String onlineKey = "live_online_user:" + item.getRoomId();
                Long perNum = redisTemplate.opsForHash().size(onlineKey);
                item.setPersonNum(perNum);
            }
        });
        return ApiResult.ok(page);
    }

    /**
     * 直播详情
     * @param id
     * @return
     */
    @GetMapping("/liveView")
    public ApiResult liveView(Long id){
        LiveBroadcast liveBroadcast = liveBroadcastService.getById(id);
        //设置直播人信息
        User userInfo = userService.getById(liveBroadcast.getUserId());
        liveBroadcast.setUserName(userInfo.getName());
        liveBroadcast.setUserAvatarUrl(userInfo.getAvatarUrl());
        List<LiveGoods> liveGoodsList = liveGoodsService.list(new LambdaQueryWrapper<LiveGoods>()
                .eq(LiveGoods::getLiveId, liveBroadcast.getId()));
        if (CollectionUtil.isNotEmpty(liveGoodsList)){
            liveGoodsList.forEach(liveGoods -> {
                Goods goods = goodsService.getById(liveGoods.getGoodsId());
                liveGoods.setName(goods.getName());
                liveGoods.setHeadPortrait(goods.getHeadPortrait());
                liveGoods.setDetail(goods.getDetail());
                liveGoods.setPrice(goods.getPrice());
                List<LiveGoodsSku> liveGoodsSkuList = liveGoodsSkuService.list(new LambdaQueryWrapper<LiveGoodsSku>()
                        .eq(LiveGoodsSku::getLiveGoodsId, liveGoods.getId())
                        .orderByAsc(LiveGoodsSku::getPrice));
                if (CollectionUtil.isNotEmpty(liveGoodsSkuList)){
                    liveGoods.setOriPrice(liveGoodsSkuList.get(0).getOriPrice());
                    liveGoods.setPrice(liveGoodsSkuList.get(0).getPrice());
                    liveGoodsSkuList.forEach(goodsSku -> {
                        GoodsSku sku = goodsSkuService.getById(goodsSku.getSkuId());
                        goodsSku.setProperties(sku.getProperties())
                                .setPic(sku.getPic())
                                .setSkuName(sku.getSkuName())
                                .setProdName(sku.getProdName())
                                .setGoodsId(sku.getGoodsId());
                        if (sku.getStocks() < goodsSku.getStocks()){
                            goodsSku.setStocks(sku.getStocks());
                        }
                    });
                }
                liveGoods.setGoodsSkuList(liveGoodsSkuList);
            });
        }
        liveBroadcast.setGoodsList(liveGoodsList);
        if (LiveEnums.LiveStatus.END_OF_LIVE.getCode().equals(liveBroadcast.getStatus())){
            Long between = DateUtil.between(liveBroadcast.getStartTime(), liveBroadcast.getEndTime(), DateUnit.MS);
            liveBroadcast.setBetween(DateUtil.formatBetween(between, BetweenFormater.Level.SECOND));
        }
        if (LiveEnums.LiveStatus.LIVE_BROADCAST.getCode().equals(liveBroadcast.getStatus())){
            LiveLink liveLink = liveLinkService.getOne(new LambdaQueryWrapper<LiveLink>()
                    .eq(LiveLink::getLiveId, liveBroadcast.getId())
                    .eq(LiveLink::getStatus, LiveEnums.LiveLinkStatus.TWO.getCode())
                    .last(" limit 1 "));
            if (ObjectUtil.isNotEmpty(liveLink)){
                User user = userService.getById(liveLink.getUserId());
                liveLink.setUserAvatarUrl(user.getAvatarUrl());
                liveLink.setUserName(user.getName());
                liveBroadcast.setLiveLink(liveLink);
            }
        }
        return ApiResult.ok(liveBroadcast);
    }

    /**
     * 修改直播商品
     * @param liveGoods
     * @return
     */
    @PostMapping("/updateLiveGoods")
    public ApiResult updateLiveGoods(@RequestBody LiveGoods liveGoods){
        liveGoodsService.updateLiveGoods(liveGoods);
        return ApiResult.ok();
    }

    /**
     * 删除直播商品
     * @param liveGoodsId
     * @return
     */
    @GetMapping("/delGoods")
    public ApiResult delGoods(@RequestBody Long liveGoodsId){
        liveGoodsService.delGoods(liveGoodsId);
        return ApiResult.ok();
    }

    /**
     * 讲解商品
     * @param liveGoodsId
     * @return
     */
    @GetMapping("/explainGoods")
    public ApiResult explainGoods(Long liveGoodsId,Long roomId,HttpServletRequest request){
        liveBroadcastService.explainGoods(liveGoodsId,roomId,request);
        return ApiResult.ok();
    }

    /**
     * 点赞
     * @param liveId
     * @param number
     * @return
     */
    @GetMapping("/praise")
    public ApiResult praise(Long liveId, Integer number){
        liveBroadcastService.praise(liveId,number);
        LiveBroadcast liveBroadcast = liveBroadcastService.getById(liveId);
        String onlineKey = "live_online_user:" + liveBroadcast.getRoomId();
        Set<String> userIds = redisTemplate.opsForHash().keys(onlineKey);
        userIds.add(liveBroadcast.getUserId().toString());
        //发送指令
        Map msg = new HashMap<>();
        msg.put("cmd", MsgCommand.LIVE_PRAISE.getCmd());
        msg.put("roomId",liveBroadcast.getRoomId());
        msg.put("praiseNum",liveBroadcast.getPraiseNum());
        PushUtils.push(userIds, JSONUtil.toJsonStr(msg));
        return ApiResult.ok();
    }

    @PostMapping("/brushGifts")
    public ApiResult brushGifts(@RequestBody LiveGiftDetail liveGiftDetail,HttpServletRequest request){
        liveBroadcastService.brushGifts(liveGiftDetail,request);
        return ApiResult.ok();
    }

    @GetMapping("/liveGoodsView")
    private ApiResult liveGoodsView(Long id){
        LiveGoods liveGoods = liveGoodsService.getById(id);
        Goods goods = goodsService.getById(liveGoods.getGoodsId());
        liveGoods.setName(goods.getName());
        liveGoods.setHeadPortrait(goods.getHeadPortrait());
        liveGoods.setDetail(goods.getDetail());
        liveGoods.setPrice(goods.getPrice());
        List<LiveGoodsSku> liveGoodsSkuList = liveGoodsSkuService.list(new LambdaQueryWrapper<LiveGoodsSku>()
                .eq(LiveGoodsSku::getLiveGoodsId, liveGoods.getId()));
        liveGoodsSkuList.forEach(goodsSku -> {
            GoodsSku sku = goodsSkuService.getById(goodsSku.getSkuId());
            goodsSku.setProperties(sku.getProperties())
                    .setPic(sku.getPic())
                    .setSkuName(sku.getSkuName())
                    .setProdName(sku.getProdName())
                    .setGoodsId(sku.getGoodsId());
            if (sku.getStocks() < goodsSku.getStocks()){
                goodsSku.setStocks(sku.getStocks());
            }
        });
        liveGoods.setGoodsSkuList(liveGoodsSkuList);
        return ApiResult.ok(liveGoods);
    }

}

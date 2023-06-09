package com.shop.tsshop.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.web.enums.LiveEnums;
import com.shop.tsshop.web.enums.NumberEnmus;
import com.shop.tsshop.web.mapper.*;
import com.shop.tsshop.web.model.domain.*;
import com.shop.tsshop.web.model.dto.TencentCallbackDto;
import com.shop.tsshop.web.model.vo.LiveUser;
import com.shop.tsshop.web.netty.protocol.command.MsgCommand;
import com.shop.tsshop.web.service.*;
import com.shop.tsshop.web.netty.utils.PushUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : tsshop
 * @date : 2022-12-19
 */
@Service
@Slf4j
public class LiveBroadcastServiceImpl extends ServiceImpl<LiveBroadcastMapper, LiveBroadcast> implements LiveBroadcastService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private LiveStoreService liveStoreService;

    @Autowired
    private LiveGoodsMapper liveGoodsMapper;

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Autowired
    private LiveGoodsSkuMapper liveGoodsSkuMapper;

    @Autowired
    private LiveLinkService liveLinkService;

    @Autowired
    private UserLiveGiftService userLiveGiftService;

    @Autowired
    private LiveGiftDetailService liveGiftDetailService;

    @Autowired
    private LiveGiftService liveGiftService;

    @Resource
    private GoodsMapper goodsMapper;

    @Autowired
    private LiveStoreIncomeLogService liveStoreIncomeLogService;


    @Override
    public Object createLive(LiveBroadcast liveBroadcast, HttpServletRequest httpServletRequest) {
        Long userId = redisService.getCurrentUser(httpServletRequest).getId();
        LiveStore liveStore = liveStoreService.getOne(new LambdaQueryWrapper<LiveStore>()
                .eq(LiveStore::getUserId, userId));
        if (ObjectUtil.isEmpty(liveStore)){
            throw new MyException("非直播小店，无法发起直播");
        }
        //查询是否已有群直播
        LiveBroadcast oldLive = Optional.ofNullable(getOne(new LambdaQueryWrapper<LiveBroadcast>()
                .in(LiveBroadcast::getStatus
                        , LiveEnums.LiveStatus.LIVE_BROADCAST.getCode()
                        , LiveEnums.LiveStatus.NOT_STARTED.getCode())
                .eq(LiveBroadcast::getUserId, userId))).orElseGet(() -> new LiveBroadcast());
        //状态为直播中的
        if (LiveEnums.LiveStatus.LIVE_BROADCAST.getCode().equals(oldLive.getStatus())) {
            throw new MyException("已存在直播");
        //存在未开始的并且缓存中没有未开始的,把状态更新为非正常结束
        } else if (LiveEnums.LiveStatus.NOT_STARTED.getCode().equals(oldLive.getStatus())) {
            oldLive.setStatus(LiveEnums.LiveStatus.NOT_STARTED_END.getCode());
            updateById(oldLive);
        }
        Long roomId = liveStore.getId();
        liveBroadcast.setRoomId(roomId);
        liveBroadcast.setLiveStoreId(liveStore.getId());
        liveBroadcast.setUserId(userId);
        //设置状态为未开始
        liveBroadcast.setStatus(LiveEnums.LiveStatus.NOT_STARTED.getCode());
        //保存直播记录
        save(liveBroadcast);
        if (CollectionUtil.isNotEmpty(liveBroadcast.getGoodsList())){
            liveBroadcast.setGoodsNum(liveBroadcast.getGoodsList().size());
            liveBroadcast.getGoodsList().forEach(goods -> {
                goods.setLiveId(liveBroadcast.getId())
                        .setLiveStoreId(liveBroadcast.getLiveStoreId());
                liveGoodsMapper.insert(goods);
                if (CollectionUtil.isNotEmpty(goods.getGoodsSkuList())){
                    goods.getGoodsSkuList().forEach(sku -> {
                        GoodsSku goodsSku = goodsSkuMapper.selectById(sku.getSkuId());
                        if (goodsSku.getStocks() < sku.getStocks()){
                            throw new MyException("库存不足");
                        }
                        sku.setLiveId(liveBroadcast.getId())
                                .setLiveGoodsId(goods.getId())
                                .setLiveId(liveBroadcast.getId())
                                .setLiveStoreId(liveBroadcast.getLiveStoreId());
                        liveGoodsSkuMapper.insert(sku);
                    });
                }
            });
        }
        return liveBroadcast;
    }

    @Override
    public void startLive(LiveBroadcast liveBroadcast, TencentCallbackDto groupCallbackPo) {
        //更新状态,开始时间
        update(new LambdaUpdateWrapper<LiveBroadcast>()
                .set(LiveBroadcast::getStatus,LiveEnums.LiveStatus.LIVE_BROADCAST.getCode())
                .set(LiveBroadcast::getStartTime,new Date(groupCallbackPo.getEventInfo().getEventMsTs()))
                .eq(LiveBroadcast::getId,liveBroadcast.getId()));
    }

    @Override
    public void addRoom(LiveBroadcast liveBroadcast, TencentCallbackDto groupCallbackPo) {
        //判断是否是观众身份
        if (!liveBroadcast.getUserId().toString().equals(groupCallbackPo.getEventInfo().getUserId())){
            User user = userService.getById(groupCallbackPo.getEventInfo().getUserId());

            LiveUser liveUser = new LiveUser()
                    .setId(user.getId())
                    .setAvatarUrl(user.getAvatarUrl())
                    .setName(user.getName());

            String liveKey = "live_user:" + liveBroadcast.getRoomId();
            String onlineKey = "live_online_user:" + liveBroadcast.getRoomId();
            //更新历史参与观众
            redisTemplate.opsForSet().add(liveKey,groupCallbackPo.getEventInfo().getUserId());
            //更新在线观众
            redisTemplate.opsForHash().put(onlineKey,groupCallbackPo.getEventInfo().getUserId(),liveUser);
            Long perNum = redisTemplate.opsForHash().size(onlineKey);
            Set<String> userIds = redisTemplate.opsForHash().keys(onlineKey);
            //发送指令
            Map msg = new HashMap<>();
            msg.put("cmd",MsgCommand.LIVE_ROOM_STATUS.getCmd());
            msg.put("type","add");
            msg.put("userName",user.getName());
            msg.put("userId",user.getId());
            msg.put("userAvatarUrl",user.getAvatarUrl());
            msg.put("perNum",perNum);
            List<String> list = new ArrayList<>();
            list.add(liveBroadcast.getUserId().toString());
            list.addAll(userIds);
            PushUtils.push(list,JSONUtil.toJsonStr(msg));
        }
    }

    @Override
    public void outRoom(LiveBroadcast liveBroadcast, TencentCallbackDto groupCallbackPo) {
        //判断是否是观众身份
        if (!liveBroadcast.getUserId().toString().equals(groupCallbackPo.getEventInfo().getUserId())){
            User user = userService.getById(groupCallbackPo.getEventInfo().getUserId());
            //更新在线观众
            String onlineKey = "live_online_user:" + liveBroadcast.getRoomId();
            redisTemplate.opsForHash().delete(onlineKey,groupCallbackPo.getEventInfo().getUserId());
            Long perNum = redisTemplate.opsForHash().size(onlineKey);
            Set<String> userIds = redisTemplate.opsForHash().keys(onlineKey);
            //发送指令
            Map msg = new HashMap<>();
            msg.put("cmd",MsgCommand.LIVE_ROOM_STATUS.getCmd());
            msg.put("type","out");
            msg.put("userName",user.getName());
            msg.put("userId",user.getId());
            msg.put("userAvatarUrl",user.getAvatarUrl());
            msg.put("perNum",perNum);
            List<String> list = new ArrayList<>();
            list.add(liveBroadcast.getUserId().toString());
            list.addAll(userIds);
            PushUtils.push(list,JSONUtil.toJsonStr(msg));
        }
    }

    @Override
    public void endLive(LiveBroadcast liveBroadcast, TencentCallbackDto groupCallbackPo) {
        String liveKey = "live_user:" + liveBroadcast.getRoomId();
        String onlineKey = "live_online_user:" + liveBroadcast.getRoomId();
        //获取本场人数
        Long num = redisTemplate.opsForSet().size(liveKey);
        //指令参数
        Map msg = new HashMap<>();
        msg.put("cmd",MsgCommand.LIVE_END.getCmd());
        msg.put("roomId", liveBroadcast.getRoomId());
        //更新状态，观看人数，结束时间
        if (update(new LambdaUpdateWrapper<LiveBroadcast>()
                .set(LiveBroadcast::getStatus,LiveEnums.LiveStatus.END_OF_LIVE.getCode())
                .set(LiveBroadcast::getEndTime,new Date(groupCallbackPo.getEventInfo().getEventMsTs()))
                .set(LiveBroadcast::getPersonNum,num)
                .eq(LiveBroadcast::getId,liveBroadcast.getId()))){
            //删除缓存的数据
            redisTemplate.delete(liveKey);
            redisTemplate.delete(onlineKey);
        }

        //给主播发送指令
        Long between = DateUtil.between(liveBroadcast.getStartTime(), new Date(groupCallbackPo.getEventInfo().getEventMsTs()), DateUnit.MS);
        msg.put("formatBetween",DateUtil.formatBetween(between,BetweenFormater.Level.SECOND));
        msg.put("personNum",num);
        //计算收益
        cash(msg,liveBroadcast);
        PushUtils.push(liveBroadcast.getUserId(),JSONUtil.toJsonStr(msg));
    }

    private void cash(Map msg, LiveBroadcast liveBroadcast) {
        //结算礼物收益
        List<LiveGiftDetail> liveGiftDetails = liveGiftDetailService.list(new LambdaQueryWrapper<LiveGiftDetail>()
                .eq(LiveGiftDetail::getLiveId, liveBroadcast.getId()));
        List<LiveGift> list = liveGiftService.list();
        Map<Long, LiveGift> liveGifMap = list.stream().collect(Collectors.toMap(LiveGift::getId, item -> item));
        BigDecimal giftProfit = new BigDecimal(0);
        Long giftNum = 0L;
        for (LiveGiftDetail liveGiftDetail : liveGiftDetails) {
            giftProfit = giftProfit.add(new BigDecimal(liveGiftDetail.getNumber())
                    .multiply(liveGifMap.get(liveGiftDetail.getGiftId()).getUnitPrice())
                    .multiply(liveGifMap.get(liveGiftDetail.getGiftId()).getGiftRate()).divide(new BigDecimal(100))
                    .setScale(2,BigDecimal.ROUND_HALF_DOWN));
            giftNum = giftNum + liveGiftDetail.getNumber();
        }
        liveStoreService.addProfit(liveBroadcast.getLiveStoreId(),giftProfit);
        liveStoreIncomeLogService.create(liveBroadcast.getLiveStoreId(),giftProfit,0);

        msg.put("giftProfit",giftProfit);

        Integer count = liveGoodsMapper.selectCount(new LambdaQueryWrapper<LiveGoods>()
                .eq(LiveGoods::getLiveId, liveBroadcast.getId()));

        update(new LambdaUpdateWrapper<LiveBroadcast>()
                .set(LiveBroadcast::getGiftProfit,giftProfit)
                .set(LiveBroadcast::getIsCash,true)
                .set(LiveBroadcast::getGiftNum,giftNum)
                .set(LiveBroadcast::getGoodsNum,count)
                .eq(LiveBroadcast::getId,liveBroadcast.getId()));

        msg.put("profitProfit",liveBroadcast.getOrderProfit());
    }

    @Override
    public IPage<LiveBroadcast> pageList(Pageable pageable, LambdaQueryWrapper<LiveBroadcast> wrapper) {
        IPage<LiveBroadcast> page = new Page<LiveBroadcast>(pageable.getPageNumber(),pageable.getPageSize());
        page = page(page,wrapper);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void explainGoods(Long liveGoodsId, Long roomId, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        LiveGoods liveGoods = liveGoodsMapper.selectById(liveGoodsId);
        liveGoods.setExplainStatus(2);
        liveGoodsMapper.update(null,new LambdaUpdateWrapper<LiveGoods>()
                .set(LiveGoods::getExplainStatus,3)
                .eq(LiveGoods::getLiveId,liveGoods.getLiveId())
                .eq(LiveGoods::getExplainStatus,2));
        liveGoodsMapper.updateById(liveGoods);
        Goods goods = goodsMapper.selectById(liveGoods.getGoodsId());
        liveGoods.setName(goods.getName());
        liveGoods.setHeadPortrait(goods.getHeadPortrait());
        liveGoods.setDetail(goods.getDetail());
        liveGoods.setPrice(goods.getPrice());
        LiveGoodsSku liveGoodsSku = liveGoodsSkuMapper.selectOne(new LambdaQueryWrapper<LiveGoodsSku>()
                .eq(LiveGoodsSku::getLiveGoodsId, liveGoods.getId())
                .orderByAsc(LiveGoodsSku::getPrice)
                .last(" limit 1 "));
        liveGoods.setOriPrice(liveGoodsSku.getOriPrice());
        liveGoods.setPrice(liveGoodsSku.getPrice());

        redisTemplate.opsForValue().set("live_explain_goods:" + roomId,liveGoodsId);
        String onlineKey = "live_online_user:" + roomId;
        Set<String> userIds = redisTemplate.opsForHash().keys(onlineKey);
        userIds.add(user.getId().toString());
        //发送指令
        Map msg = new HashMap<>();
        msg.put("cmd",MsgCommand.EXPLAIN_GOODS.getCmd());
        msg.put("goods",liveGoods);
        msg.put("roomId",roomId);
        PushUtils.push(userIds, JSONUtil.toJsonStr(msg));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void link(LiveLink liveLink, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        LiveLink oldLiveLink = liveLinkService.getById(liveLink.getId());
        if (oldLiveLink.getIsFromUser() && LiveEnums.LiveLinkStatus.TWO.getCode().equals(liveLink.getStatus())) {
            if (!user.getId().equals(oldLiveLink.getLiveUserId())){
                throw new MyException("无权限操作");
            }
        }
        LiveBroadcast broadcast = getById(oldLiveLink.getLiveId());
        if (LiveEnums.LiveLinkStatus.TWO.getCode().equals(liveLink.getStatus())){
            int count = liveLinkService.count(new LambdaQueryWrapper<LiveLink>()
                    .eq(LiveLink::getLiveId, liveLink.getLiveId())
                    .eq(LiveLink::getStatus, LiveEnums.LiveLinkStatus.TWO.getCode()));
            if (count > 0){
                throw new MyException("无法重复接听");
            }
            baseMapper.addLinkNum(broadcast.getId());
            oldLiveLink.setStartTime(new Date());
        } else if (LiveEnums.LiveLinkStatus.THREE.getCode().equals(liveLink.getStatus())){
            oldLiveLink.setEndTime(new Date());
        }
        oldLiveLink.setStatus(liveLink.getStatus());
        liveLinkService.updateById(liveLink);
        User linkUser = userService.getById(oldLiveLink.getUserId());
        //发送指令
        Map msg = new HashMap<>();
        msg.put("cmd",MsgCommand.LIVE_LINK.getCmd());
        msg.put("liveId",liveLink.getLiveId());
        msg.put("linkId",liveLink.getId());
        msg.put("userName",linkUser.getName());
        msg.put("userId",linkUser.getId());
        msg.put("userAvatarUrl",linkUser.getAvatarUrl());

        String onlineKey = "live_online_user:" + broadcast.getRoomId();
        Set<String> userIds = redisTemplate.opsForHash().keys(onlineKey);
        userIds.add(oldLiveLink.getUserId().toString());
        userIds.add(oldLiveLink.getLiveUserId().toString());
        if (oldLiveLink.getIsFromUser()){
            msg.put("status",liveLink.getStatus());
            PushUtils.push(userIds,JSONUtil.toJsonStr(msg));
        } else {
            msg.put("status",liveLink.getStatus());
            PushUtils.push(userIds,JSONUtil.toJsonStr(msg));
        }
    }

    @Override
    public void praise(Long liveId, Integer number) {
        baseMapper.praise(liveId,number);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void brushGifts(LiveGiftDetail liveGiftDetail, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        Long number = liveGiftDetail.getNumber();
        LiveBroadcast liveBroadcast = getById(liveGiftDetail.getLiveId());
        liveGiftDetail.setToUserId(liveBroadcast.getUserId());
        liveGiftDetail.setFromUserId(user.getId());
        UserLiveGift userLiveGift = userLiveGiftService.getOne(new LambdaQueryWrapper<UserLiveGift>()
                .eq(UserLiveGift::getUserId, user.getId())
                .eq(UserLiveGift::getLiveGiftId, liveGiftDetail.getGiftId())
                .last(" limit 1 "));
        if (ObjectUtil.isEmpty(userLiveGift) || userLiveGift.getLiveGiftNum() < number){
            throw new MyException("礼物数量不足");
        }
        userLiveGiftService.deductionNumber(userLiveGift.getId(),number);
        liveGiftDetailService.save(liveGiftDetail);
        Set<String> userIds = redisTemplate.opsForHash().keys("live_online_user:" + liveBroadcast.getRoomId());
        userIds.add(liveBroadcast.getUserId().toString());
        LiveGift liveGift = liveGiftService.getById(liveGiftDetail.getGiftId());
        Map msg = new HashMap<>();
        msg.put("cmd", MsgCommand.LIVE_GIVE_GIFT.getCmd());
        msg.put("userName",user.getName());
        msg.put("number",liveGiftDetail.getNumber());
        msg.put("url",liveGift.getFront());
        msg.put("giftName",liveGift.getGiftName());
        msg.put("roomId",liveBroadcast.getRoomId());
        PushUtils.push(userIds,JSONUtil.toJsonStr(msg));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createLink(LiveLink liveLink, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        if (liveLink.getIsFromUser()){
            liveLink.setUserId(user.getId());
        }else {
            liveLink.setLiveUserId(user.getId());
        }

        liveLinkService.update(new LambdaUpdateWrapper<LiveLink>()
                .set(LiveLink::getStatus, LiveEnums.LiveLinkStatus.FOUR.getCode())
                .eq(LiveLink::getLiveId, liveLink.getLiveId())
                .eq(LiveLink::getLiveUserId, liveLink.getLiveUserId())
                .eq(LiveLink::getUserId, liveLink.getUserId())
                .eq(LiveLink::getStatus, LiveEnums.LiveLinkStatus.ONE.getCode())
                .eq(LiveLink::getIsFromUser,liveLink.getIsFromUser()));

        liveLink.setStatus(LiveEnums.LiveLinkStatus.ONE.getCode());
        liveLinkService.save(liveLink);
        //发送指令
        Map msg = new HashMap<>();
        msg.put("cmd",MsgCommand.LIVE_LINK_APPLY.getCmd());
        msg.put("liveId",liveLink.getLiveId());
        msg.put("linkId",liveLink.getId());
        msg.put("userName",user.getName());
        msg.put("userId",user.getId());
        msg.put("userAvatarUrl",user.getAvatarUrl());
        if (liveLink.getIsFromUser()){
            msg.put("status",liveLink.getStatus());
            PushUtils.push(liveLink.getLiveUserId(),JSONUtil.toJsonStr(msg));
        } else {
            msg.put("status",liveLink.getStatus());
            PushUtils.push(liveLink.getUserId(),JSONUtil.toJsonStr(msg));
        }
    }

    @Override
    public void addProfit(Long liveId, BigDecimal profit) {
        baseMapper.addProfit(liveId,profit);
    }

}

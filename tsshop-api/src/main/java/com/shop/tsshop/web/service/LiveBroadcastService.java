package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.LiveBroadcast;
import com.shop.tsshop.web.model.domain.LiveGiftDetail;
import com.shop.tsshop.web.model.domain.LiveLink;
import com.shop.tsshop.web.model.dto.TencentCallbackDto;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author : tsshop
 * @date : 2022-12-19
 */
public interface LiveBroadcastService extends IService<LiveBroadcast> {
    /**
     * 创建直播
     * @param liveBroadcast             dto
     * @param httpServletRequest        request
     * @return                          obj
     */
    Object createLive(LiveBroadcast liveBroadcast, HttpServletRequest httpServletRequest);

    /**
     * 开始直播
     * @param liveBroadcast             dto
     * @param groupCallbackPo           直播回调
     */
    void startLive(LiveBroadcast liveBroadcast, TencentCallbackDto groupCallbackPo);

    /**
     * 进入房间
     * @param liveBroadcast             dto
     * @param groupCallbackPo           直播回调
     */
    void addRoom(LiveBroadcast liveBroadcast, TencentCallbackDto groupCallbackPo);

    /**
     * 退出房间
     * @param liveBroadcast             dto
     * @param groupCallbackPo           直播回调
     */
    void outRoom(LiveBroadcast liveBroadcast, TencentCallbackDto groupCallbackPo);

    /**
     * 结束直播
     * @param liveBroadcast             dto
     * @param groupCallbackPo           直播回调
     */
    void endLive(LiveBroadcast liveBroadcast, TencentCallbackDto groupCallbackPo);

    /**
     * 查询直播记录
     * @param pageable                  分页参数
     * @param wrapper                   wrapper
     * @return                          统一返回
     */
    IPage<LiveBroadcast> pageList(Pageable pageable, LambdaQueryWrapper<LiveBroadcast> wrapper);

    /**
     * 上架商品
     * @param liveGoodsId               商品 ID
     * @param roomId                    房间 ID
     * @param request                   request
     */
    void explainGoods(Long liveGoodsId, Long roomId, HttpServletRequest request);

    /**
     * 连线
     * @param liveLink                  直播连麦
     * @param request                   request
     */
    void link(LiveLink liveLink, HttpServletRequest request);

    /**
     * 点赞
     * @param liveId                    直播 ID
     * @param number                    点赞数
     */
    void praise(Long liveId, Integer number);

    /**
     * 赠送礼物
     * @param liveGiftDetail            礼物信息
     * @param request                   request
     */
    void brushGifts(LiveGiftDetail liveGiftDetail, HttpServletRequest request);

    /**
     * 创建连麦
     * @param liveLink                  连麦信息
     * @param request                   request
     */
    void createLink(LiveLink liveLink, HttpServletRequest request);

    /**
     * 添加直播记录
     * @param liveId                    直播 ID
     * @param profit                    收益
     */
    void addProfit(Long liveId, BigDecimal profit);
}

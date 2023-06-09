package com.ts.shop.domain.vo;

import lombok.Data;

/**
 * @ClassName waitMatterVo
 * @Author TS SHOP
 * @create 2023/6/7
 */
@Data
public class WaitMatterVo {
    /**
     * 退款申请条目数
     */
    private Long returnOrderNum;
    /**
     * 待发货订单数
     */
    private Long waitDeliverOrderNum;
    /**
     * 直播小店申请
     */
    private Long liveStoreApplyNum;
    /**
     * 待收货订单数
     */
    private Long waitReceivingOrderNum;
}

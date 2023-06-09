package com.ts.shop.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName LiveGiftOrderListVo
 * @Author TS SHOP
 * @create 2023/5/31
 */
@Data
public class LiveGiftOrderListVo {
    /**
     * 订单 ID
     */
    private Long orderId;
    /**
     * 用户 ID
     */
    private Long userId;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 礼物 id
     */
    private Long liveGiftId;
    /**
     * 礼物名称
     */
    private String liveGiftName;
    /**
     * 礼物数量
     */
    private Integer liveGiftNum;
    /**
     * 支付金额
     */
    private BigDecimal payAmt;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}

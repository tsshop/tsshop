package com.ts.shop.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName LiveGiftOrderListDto
 * @Author TS SHOP
 * @create 2023/5/31
 */
@Data
public class LiveGiftOrderListDto extends PageDto{
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
     * 支付时间条件  开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String payStartTime;
    /**
     * 支付时间条件  结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String payEndTime;

    /**
     * 创建时间条件  开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createStartTime;
    /**
     * 创建时间条件  结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createEndTime;
}

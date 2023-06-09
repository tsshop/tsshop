package com.shop.tsshop.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author sunyawei
 */
@Getter
@AllArgsConstructor
public enum StoreOrderStatusEnum {
    /**
     * 待处理
     */
    WAIT_PENDING(10, "待处理"),
    /**
     * 待支付定金
     */
    WAIT_PAY_DEPOSIT(20, "待收定金"),
    /**
     * 待发货
     */
    WAIT_DELIVERY(30, "待发货"),
    /**
     * 待收货
     */
    WAIT_LAST_PAY(40, "待收尾款"),
    /**
     * 已完成
     */
    FINISHED(50, "已完成"),
    /**
     * 退款中
     */
    wait_refund(0, "已取消"),
    /**
     * 退款完成
     */
    refund_finish(-1, "退款完成"),
    /**
     * 退款失败
     */
    refund_fail(-2, "退款失败");


    private Integer value;
    private String msg;

    public static StoreOrderStatusEnum toType(int value) {
        return Stream.of(StoreOrderStatusEnum.values())
                .filter(p -> p.value == value)
                .findAny()
                .orElse(null);
    }

    public static String getMsg(int status) {
        for (StoreOrderStatusEnum c : StoreOrderStatusEnum.values()) {
            if (c.value.compareTo(status) == 0) {
                return c.msg;
            }
        }
        return null;
    }
}

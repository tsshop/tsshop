package com.shop.tsshop.web.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 退款表
 * </p>
 *
 * @author fuhuilei
 * @since 2022-09-26
 */
@Data
public class ReturnApplyDto implements Serializable {


    /**
     * 订单号
     */
    @NotBlank(message = "订单号不能为空")
    private String orderNo;


    /**
     * 申请退款金额
     */
    @NotBlank(message = "退款金额不能为空")
    private BigDecimal returnAmt;

    /**
     * 申请退款理由
     */
    private String returnReason;

    /**
     * 申请退款图片
     */
    private String returnImg;

    /**
     * 申请退款类型 1：仅退款（没收到货）2：仅退款（收到货了）3：退货退款
     */
    @NotBlank(message = "退款类型不能为空")
    private Integer returnType;

    /**
     * 申请退款文字描述
     */
    private String returnDetail;


}

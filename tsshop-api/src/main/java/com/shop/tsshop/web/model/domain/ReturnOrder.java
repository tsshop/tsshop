package com.shop.tsshop.web.model.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 退款表
 * </p>
 *
 * @author xu
 * @since 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("return_order")
public class ReturnOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 真实退款金额
     */
    private BigDecimal reallyAmt;

    /**
     * 订单退款金额
     */
    private BigDecimal orderAmt;

    /**
     * 申请退款金额
     */
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
    private Integer returnType;

    /**
     * 申请退款文字描述
     */
    private String returnDetail;

    /**
     * 退货地址
     */
    private Long addressId;

    /**
     * 1:发起退货（等待审核） 2：审核拒绝 3：退款成功 4 等待填物流单号 5 物流待审核
     */
    private Integer status;

    /**
     * 物流名字
     */
    private String expressName;

    /**
     * 物流单号
     */
    private String expressNo;

    /**
     * 物流编码
     */
    private String express;

    /**
     * 创建时间/下单时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 拒绝理由
     */
    private String refuseDetail;

    /**
     * 退单号
     */
    private String outRefundNo;


}

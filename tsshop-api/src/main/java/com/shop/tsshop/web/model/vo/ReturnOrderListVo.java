package com.shop.tsshop.web.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReturnOrderListVo {
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
     * 申请退款类型 1：仅退款（没收到货）2：仅退款（收到货了）3：退货退款
     */
    private Integer returnType;


    /**
     * 1:发起退货（等待审核） 2：审核拒绝 3：退款成功 4 等待填物流单号 5 物流待审核
     */
    private Integer status;


    /**
     * 创建时间/下单时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    private String gPic;
    private String gPrice;
    private String sPrice;
    private String sPic;
    private String properties;
    private String name;


}

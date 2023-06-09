package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shop.tsshop.web.model.domain.pay.AlipayParams;
import com.shop.tsshop.web.model.domain.pay.UnionPayParams;
import com.shop.tsshop.web.model.domain.pay.WeChatAppParams;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付通道;
 * @author : tsshop
 * @date : 2023-4-23
 */
@Data
@TableName("pay_thoroughfare")
public class PayThoroughfare implements Serializable{

    /** id */
    private Long id ;

    /** 名称 */
    private String name ;

    /** 接口id */
    private Long interfaceId ;

    /** 接口名称 */
    private String interfaceName ;

    /** 支付提供方 */
    private String payProvider ;

    /** 配置 */
    private String config ;

    /** 支持的方式 */
    private String payTypes ;

    /** 是否启用 */
    private Integer isUse ;

    /** 创建时间 */
    private Date createTime ;

    /** 更新时间 */
    private Date updateTime ;

    @TableField(exist = false)
    private AlipayParams alipayParams;

    @TableField(exist = false)
    private UnionPayParams unionPayParams;

    @TableField(exist = false)
    private WeChatAppParams weChatAppParams;

    /**
     * 是否支持提现
     */
    private Boolean supportWithdrawals;

}

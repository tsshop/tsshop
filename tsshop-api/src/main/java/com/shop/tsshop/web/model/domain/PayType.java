package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付方式;
 * @author : tsshop
 * @date : 2023-4-24
 */
@Data
@TableName("pay_type")
public class PayType implements Serializable{

    /** id */
    private Long id ;

    /** logo */
    private String logo ;

    /** 名称 */
    private String name ;

    /** 类型 */
    private String type ;

    /** 类型名称 */
    private String typeName ;

    /** 创建时间 */
    private Date createTime ;

    /** 更新时间 */
    private Date updateTime ;

}
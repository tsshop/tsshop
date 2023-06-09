package com.shop.tsshop.web.model.domain;

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
 * 用户收货地址表
 * </p>
 *
 * @author xu
 * @since 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("return_address")
public class ReturnAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收货地址ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 收货人姓名
     */
    private String name;

    /**
     * 收货电话
     */
    private String phone;

    /**
     * 省ID
     */
    private Long province;

    /**
     * 市ID
     */
    private Long city;

    /**
     * 区ID
     */
    private Long area;

    /**
     * 详细地址
     */
    private String detailAddr;

    /**
     * 是否已删除 （1：正常/-1：删除）
     */
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否默认收货地址（2：否/1：是)
     */
    private Boolean defaultCargo;


    /**
     * 省
     */
    @TableField(exist = false)
    private String provinceName;

    /**
     * 市
     */
    @TableField(exist = false)
    private String cityName;

    /**
     * 区
     */
    @TableField(exist = false)
    private String areaName;

}

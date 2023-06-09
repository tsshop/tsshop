package com.ts.shop.domain;


import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 支付方式对象 pay_type
 *
 * @author xu
 * @date 2023-04-25
 */
@Data
public class PayType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** logo */
    @Excel(name = "logo")
    private String logo;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String typeName;
}

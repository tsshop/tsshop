package com.ts.shop.domain;


import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 支付接口对象 pay_interface
 *
 * @author xu
 * @date 2023-04-25
 */
@Data
public class PayInterface extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 支付提供方 */
    @Excel(name = "支付提供方")
    private String payProvider;

    /** 配置模板 */
    @Excel(name = "配置模板")
    private String configTemplate;

    /** 支持的方式 */
    @Excel(name = "支持的方式")
    private String payTypes;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Boolean isUse;
}

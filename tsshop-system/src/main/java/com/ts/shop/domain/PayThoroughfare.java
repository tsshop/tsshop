package com.ts.shop.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;
import com.ts.shop.domain.pay.AlipayParams;
import com.ts.shop.domain.pay.UnionPayParams;
import com.ts.shop.domain.pay.WeChatAppParams;
import lombok.Data;

/**
 * 支付通道对象 pay_thoroughfare
 *
 * @author xu
 * @date 2023-04-25
 */
@Data
public class PayThoroughfare extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 接口id */
    @Excel(name = "接口id")
    private Long interfaceId;

    /** 接口名称 */
    @Excel(name = "接口名称")
    private String interfaceName;

    /** 支付提供方 */
    @Excel(name = "支付提供方")
    private String payProvider;

    /** 配置 */
    @Excel(name = "配置")
    private String config;

    /** 支持的方式 */
    @Excel(name = "支持的方式")
    private String payTypes;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Boolean isUse;

    @TableField(exist = false)
    private PayInterface payInterface;
    @TableField(exist = false)
    private AlipayParams alipayParams;

    @TableField(exist = false)
    private UnionPayParams unionPayParams;

    @TableField(exist = false)
    private WeChatAppParams weChatAppParams;

    @Excel(name = "是否启用")
    private Boolean supportWithdrawals;
}

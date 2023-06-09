package com.shop.tsshop.web.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @ClassName CreateWithdrawalsAccountDto
 * @Author TS SHOP
 * @create 2023/6/1
 */
@Data
public class CreateWithdrawalsAccountDto {
    /**
     * 验证码
     */
    private String code;

    /**
     * 用户姓名
     */
    private String fullName;

    /**
     * 账户号
     */
    private String accountNumber;

    /**
     * 账户类型 枚举
     */
    private String accountType;
}

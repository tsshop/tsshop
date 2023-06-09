package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveStoreWithdrawalsAccount
 * @Author TS SHOP
 * @create 2023/6/1
 */

/**
    * 用戶提现账户
    */
@Data
@TableName(value = "live_store_withdrawals_account")
public class LiveStoreWithdrawalsAccount {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用戶 ID
     */
    @TableField(value = "live_store_id")
    private Long liveStoreId;

    /**
     * 用户姓名
     */
    @TableField(value = "full_name")
    private String fullName;

    /**
     * 账户号
     */
    @TableField(value = "account_number")
    private String accountNumber;

    /**
     * 删除标志
     */
    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 账户类型
     */
    @TableField(value = "account_type")
    private String accountType;
}
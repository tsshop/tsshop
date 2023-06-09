package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName TsshopConfig
 * @Author TS SHOP
 * @create 2023/6/6
 */

/**
    * 配置
    */
@Data
@TableName(value = "tsshop_config")
public class TsshopConfig {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 键
     */
    @TableField(value = "config_key")
    private String configKey;

    /**
     * 值
     */
    @TableField(value = "config_value")
    private String configValue;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

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
}
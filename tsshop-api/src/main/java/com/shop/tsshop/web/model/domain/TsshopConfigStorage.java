package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName TsshopConfigStorage
 * @Author TS SHOP
 * @create 2023/6/6
 */

@Data
@TableName(value = "tsshop_config_storage")
public class TsshopConfigStorage {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 开启状态
     */
    @TableField(value = "open_status")
    private Boolean openStatus;

    /**
     * 存储类型
     */
    @TableField(value = "storage_type")
    private String storageType;

    /**
     * 存储位置
     */
    @TableField(value = "storage_location")
    private String storageLocation;

    /**
     * 配置信息
     */
    @TableField(value = "config")
    private String config;

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
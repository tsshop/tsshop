package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveStoreApply
 * @Author TS SHOP
 * @create 2023/5/24
 */

/**
    * 直播小店申请
    */
@Data
@TableName(value = "live_store_apply")
public class LiveStoreApply {
    /**
     * 主键
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 负责人姓名
     */
    @TableField(value = "manager_name")
    private String managerName;

    /**
     * 负责人手机号
     */
    @TableField(value = "manager_phone")
    private String managerPhone;

    /**
     * 负责人身份证号
     */
    @TableField(value = "manager_id_card")
    private String managerIdCard;

    /**
     * 店铺类型  0 个人 1 商户
     */
    @TableField(value = "live_store_type")
    private Integer liveStoreType;

    /**
     * 审核状态 0 审核中 1 审核成功 2 审核失败
     */
    @TableField(value = "audit_status")
    private Integer auditStatus;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 身份证正面照片
     */
    @TableField(value = "id_card_obverse")
    private String idCardObverse;

    /**
     * 身份证反面照片
     */
    @TableField(value = "id_card_reverse")
    private String idCardReverse;

    /**
     * 营业执照
     */
    @TableField(value = "business_license")
    private String businessLicense;

    /**
     * 企业名称
     */
    @TableField(value = "enterprise_name")
    private String enterpriseName;

    /**
     * 企业统一信用代码
     */
    @TableField(value = "enterprise_credit_code")
    private String enterpriseCreditCode;

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
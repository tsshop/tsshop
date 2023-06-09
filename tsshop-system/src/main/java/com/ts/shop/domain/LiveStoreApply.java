package com.ts.shop.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 直播小店申请对象 live_store_apply
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
@Data
public class LiveStoreApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    @TableField
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 负责人姓名 */
    @Excel(name = "负责人姓名")
    private String managerName;

    /** 负责人手机号 */
    @Excel(name = "负责人手机号")
    private String managerPhone;

    /** 负责人身份证号 */
    @Excel(name = "负责人身份证号")
    private String managerIdCard;

    /** 店铺类型  0 个人 1 商户 */
    @Excel(name = "店铺类型  0 个人 1 商户")
    private Integer liveStoreType;

    /** 审核状态 0 审核中 1 审核成功 2 审核失败 */
    @Excel(name = "审核状态 0 审核中 1 审核成功 2 审核失败")
    private Integer auditStatus;

    /** 身份证正面照片 */
    private String idCardObverse;

    /** 身份证反面照片 */
    private String idCardReverse;

    /** 营业执照 */
    private String businessLicense;

    /** 企业名称 */
    private String enterpriseName;

    /** 企业统一信用代码 */
    private String enterpriseCreditCode;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setManagerName(String managerName) 
    {
        this.managerName = managerName;
    }

    public String getManagerName() 
    {
        return managerName;
    }
    public void setManagerPhone(String managerPhone) 
    {
        this.managerPhone = managerPhone;
    }

    public String getManagerPhone() 
    {
        return managerPhone;
    }
    public void setManagerIdCard(String managerIdCard) 
    {
        this.managerIdCard = managerIdCard;
    }

    public String getManagerIdCard() 
    {
        return managerIdCard;
    }
    public void setLiveStoreType(Integer liveStoreType) 
    {
        this.liveStoreType = liveStoreType;
    }

    public Integer getLiveStoreType() 
    {
        return liveStoreType;
    }
    public void setAuditStatus(Integer auditStatus) 
    {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditStatus() 
    {
        return auditStatus;
    }
    public void setIdCardObverse(String idCardObverse) 
    {
        this.idCardObverse = idCardObverse;
    }

    public String getIdCardObverse() 
    {
        return idCardObverse;
    }
    public void setIdCardReverse(String idCardReverse) 
    {
        this.idCardReverse = idCardReverse;
    }

    public String getIdCardReverse() 
    {
        return idCardReverse;
    }
    public void setBusinessLicense(String businessLicense) 
    {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicense() 
    {
        return businessLicense;
    }
    public void setEnterpriseName(String enterpriseName) 
    {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseName() 
    {
        return enterpriseName;
    }
    public void setEnterpriseCreditCode(String enterpriseCreditCode) 
    {
        this.enterpriseCreditCode = enterpriseCreditCode;
    }

    public String getEnterpriseCreditCode() 
    {
        return enterpriseCreditCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("managerName", getManagerName())
            .append("managerPhone", getManagerPhone())
            .append("managerIdCard", getManagerIdCard())
            .append("liveStoreType", getLiveStoreType())
            .append("auditStatus", getAuditStatus())
            .append("remark", getRemark())
            .append("idCardObverse", getIdCardObverse())
            .append("idCardReverse", getIdCardReverse())
            .append("businessLicense", getBusinessLicense())
            .append("enterpriseName", getEnterpriseName())
            .append("enterpriseCreditCode", getEnterpriseCreditCode())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

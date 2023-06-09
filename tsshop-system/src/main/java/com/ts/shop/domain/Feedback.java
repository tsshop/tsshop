package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 反馈对象 feedback
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class Feedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 反馈ID */
    @Excel(name = "反馈ID")
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 反馈类型 */
    @Excel(name = "反馈类型")
    private String type;

    /** 内容 */
    @Excel(name = "内容")
    private String detail;

    /** 图片 */
    @Excel(name = "图片")
    private String image;

    /** 1.待处理，2.已处理，3无需处理 */
    @Excel(name = "1.待处理，2.已处理，3无需处理")
    private Integer status;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 处理意见 */
    @Excel(name = "处理意见")
    private String opinion;

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
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setDetail(String detail) 
    {
        this.detail = detail;
    }

    public String getDetail() 
    {
        return detail;
    }
    public void setImage(String image) 
    {
        this.image = image;
    }

    public String getImage() 
    {
        return image;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setOpinion(String opinion) 
    {
        this.opinion = opinion;
    }

    public String getOpinion() 
    {
        return opinion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("type", getType())
            .append("detail", getDetail())
            .append("image", getImage())
            .append("status", getStatus())
            .append("name", getName())
            .append("phone", getPhone())
            .append("opinion", getOpinion())
            .toString();
    }
}

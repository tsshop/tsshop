package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 用户对象 user
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class User extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long id;

    /** 昵称 */
    @Excel(name = "昵称")
    private String name;

    /** 头像 */
    @Excel(name = "头像")
    private String avatarUrl;

    /** 性别：0：未知 1-男，2：女 */
    @Excel(name = "性别：0：未知 1-男，2：女")
    private String gender;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 明文密码 */
    @Excel(name = "明文密码")
    private String visiblePassword;

    /** 封禁状态：0：正常，1：封禁(不允许登录) */
    @Excel(name = "封禁状态：0：正常，1：封禁(不允许登录)")
    private Integer deleted;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String openid;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setAvatarUrl(String avatarUrl) 
    {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() 
    {
        return avatarUrl;
    }
    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setVisiblePassword(String visiblePassword) 
    {
        this.visiblePassword = visiblePassword;
    }

    public String getVisiblePassword() 
    {
        return visiblePassword;
    }
    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }
    public void setOpenid(String openid) 
    {
        this.openid = openid;
    }

    public String getOpenid() 
    {
        return openid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("avatarUrl", getAvatarUrl())
            .append("gender", getGender())
            .append("phone", getPhone())
            .append("password", getPassword())
            .append("visiblePassword", getVisiblePassword())
            .append("deleted", getDeleted())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("openid", getOpenid())
            .toString();
    }
}

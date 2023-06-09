package com.shop.tsshop.web.model.vo;

import lombok.Data;

/**
 * @author GAGA
 */
@Data
public class UserInfoVo {

    /**
     * 主键id
     */
    private Long id;


    /**
     * 昵称
     */
    private String name;

    /**
     * 实名名称
     */
    private String fullName;

    /**
     * 头像
     */
    private String avatarUrl;

    private Integer gender;

    /**
     * 手机号
     */
    private String phone;
    /**
     * 备注
     */
    private String remark;

}

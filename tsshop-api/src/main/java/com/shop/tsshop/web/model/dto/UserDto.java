package com.shop.tsshop.web.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author GAGA
 */
@Data
public class UserDto {

    /**
     * id
     */
    private Long id;
    /**
     *昵称
     */
    private String name;
    /**
     * 头像
     */
    private String avatarUrl;

    private String password;
    private String code;
    private String phone;
    private Integer gender;
}

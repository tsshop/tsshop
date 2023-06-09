package com.shop.tsshop.web.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author GAGA
 */
@Data
public class AddressDto {

    // "id")
    private Long id;

    // "用户id")
    private Long userId;


    /**
     * 收货人姓名
     */
    // "收货人姓名")
    @NotNull(message = "收货人姓名不能为空")
    private String name;

    /**
     * 收货电话
     */
    // "收货人电话")
    @NotNull(message = "收货人电话不能为空")
    private String phone;

    /**
     * 详细地址
     */
    // "详细地址")
    @NotNull(message = "详细地址不能为空")
    private String detailAddr;

    /**
     * 详细地址
     */
    // "省")
    @NotNull(message = "省")
    private Long province;

    /**
     * 详细地址
     */
    // "市")
    @NotNull(message = "市")
    private Long city;

    /**
     * 详细地址
     */
    // "县")
    @NotNull(message = "县")
    private Long area;

    /**
     * 是否默认收货地址（2：否/1：是)
     */
    // "是否默认收货地址（2：否/1：是)")
    @NotNull(message = "是否默认收货地址（2：否/1：是)")
    private Integer defaultCargo;
}

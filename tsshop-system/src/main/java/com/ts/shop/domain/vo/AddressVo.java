package com.ts.shop.domain.vo;

import lombok.Data;

/**
 * @author GAGA
 */
@Data
public class AddressVo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;


    /**
     * 收货人姓名
     */
    private String name;

    /**
     * 收货电话
     */
    private String phone;

    /**
     * 市ID
     */
    private Long city;

    /**
     * 区ID
     */
    private Long area;

    /**
     * 省
     */
    private String provinceName;

    /**
     * 市
     */
    private String cityName;

    /**
     * 区
     */
    private String areaName;

    /**
     * 详细地址
     */
    private String detailAddr;



}

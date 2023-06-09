package com.ts.shop.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Transcity implements Serializable {
    private Long transcityId;

    /**
     * 运费项id
     */

    private Long transfeeId;

    /**
     * 城市id
     */

    private Long cityId;

    /**
     * 城市名称
     */
    private String areaName;
}
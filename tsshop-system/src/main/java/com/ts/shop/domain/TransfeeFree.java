package com.ts.shop.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TransfeeFree implements Serializable {
    private static final long serialVersionUID = -2811714952219888223L;
    /**
     * 指定条件包邮项id
     */
    private Long transfeeFreeId;

    /**
     * 运费模板id
     */
    private Long transportId;

    /**
     * 包邮方式 （0 满x件/重量/体积包邮 1满金额包邮 2满x件/重量/体积且满金额包邮）
     */
    private Integer freeType;

    /**
     * 需满金额
     */
    private Double amount;

    /**
     * 包邮x件/重量/体积
     */
    private Double piece;

    /**
     * 指定条件包邮城市项
     */
    private List<Area> freeCityList;
}

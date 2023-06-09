package com.ts.shop.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Transfee implements Serializable {
    private static final long serialVersionUID = 8039640964056626028L;
    /**
     * 运费项id
     */
    private Long transfeeId;

    /**
     * 运费模板id
     */
    private Long transportId;

    /**
     * 续件数量
     */
    private Double continuousPiece;

    /**
     * 首件数量
     */
    private Double firstPiece;

    /**
     * 续件费用
     */
    private Double continuousFee;

    /**
     * 首件费用
     */
    private Double firstFee;

    private List<Area> cityList;

}

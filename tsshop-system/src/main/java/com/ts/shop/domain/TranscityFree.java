package com.ts.shop.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class TranscityFree implements Serializable {
    private static final long serialVersionUID = 2579465286635831076L;
    /**
     * 指定条件包邮城市项id
     */
    private Long transcityFreeId;
    /**
     * 指定条件包邮项id
     */
    private Long transfeeFreeId;
    /**
     * 城市id
     */
    private Long freeCityId;
}

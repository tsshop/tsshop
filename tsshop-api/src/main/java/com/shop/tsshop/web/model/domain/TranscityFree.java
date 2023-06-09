package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("transcity_free")
public class TranscityFree implements Serializable {
    private static final long serialVersionUID = 2579465286635831076L;
    /**
     * 指定条件包邮城市项id
     */
    @TableId

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

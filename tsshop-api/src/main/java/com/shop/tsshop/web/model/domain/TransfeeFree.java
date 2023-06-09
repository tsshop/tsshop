package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.shop.tsshop.web.model.domain.Area;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@TableName("transfee_free")
public class TransfeeFree implements Serializable {
    private static final long serialVersionUID = -2811714952219888223L;
    /**
     * 指定条件包邮项id
     */
    @TableId
    //"指定条件包邮项id",required=true)
    private Long transfeeFreeId;

    /**
     * 运费模板id
     */

    //"运费模板id",required=true)
    private Long transportId;

    /**
     * 包邮方式 （0 满x件/重量/体积包邮 1满金额包邮 2满x件/重量/体积且满金额包邮）
     */

    //"包邮方式 （0 满x件/重量/体积包邮 1满金额包邮 2满x件/重量/体积且满金额包邮）",required=true)
    private Integer freeType;

    /**
     * 需满金额
     */
    //"需满金额",required=true)
    private BigDecimal amount;

    /**
     * 包邮x件/重量/体积
     */
    //"包邮x件/重量/体积",required=true)
    private BigDecimal piece;

    /**
     * 指定条件包邮城市项
     */
    @TableField(exist=false)
    //"指定条件包邮城市项",required=true)
    private List<Area> freeCityList;
}

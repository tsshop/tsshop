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
@TableName("transfee")
public class Transfee implements Serializable {
    private static final long serialVersionUID = 8039640964056626028L;
    /**
     * 运费项id
     */
    @TableId
    //"运费项id", required = true)
    private Long transfeeId;

    /**
     * 运费模板id
     */

    //"运费模板id", required = true)
    private Long transportId;

    /**
     * 续件数量
     */

    //"续件数量", required = true)
    private BigDecimal continuousPiece;

    /**
     * 首件数量
     */

    //"首件数量", required = true)
    private BigDecimal firstPiece;

    /**
     * 续件费用
     */

    //"续件费用", required = true)
    private BigDecimal continuousFee;

    /**
     * 首件费用
     */

    //"首件费用", required = true)
    private BigDecimal firstFee;

    @TableField(exist = false)
    //"指定条件运费城市项", required = true)
    private List<Area> cityList;

}

package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 规格参数表
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("goods_prop_value")
public class GoodsPropValue implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性值ID
     */
    @TableId(value = "value_id", type = IdType.AUTO)
    private Long valueId;

    /**
     * 属性值名称
     */
    private String propValue;

    /**
     * 属性ID
     */
    private Long propId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}

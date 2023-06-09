package com.shop.tsshop.web.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GoodsTypeVo {
    /**
     * 商品类型ID
     */
    private Long id;

    /**
     * 商品类型图标
     */
    private String avatar;

    private String banner;
    /**
     * 商品类别名称
     */
    private String name;

    private List<GoodsTypeVo> node;

}

package com.shop.tsshop.web.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 版本表
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
@Data
public class EditionVo implements Serializable {

    /**
     * 版本编号
     */
    private String verionNo;

    /**
     * 下载链接
     */
    private String url;
    private String remark;

    /**
     * 更新时间
     */
    private Date updateTime;



}

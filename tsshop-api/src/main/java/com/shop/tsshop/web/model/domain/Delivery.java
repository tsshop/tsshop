package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 物流公司
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 物流公司名称
     */
    private String dvyName;

    /**
     * 公司主页
     */
    private String companyHomeUrl;

    /**
     * 建立时间
     */
    private Date recTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 物流查询接口
     */
    private String queryUrl;

    /**
     * 快递鸟编码
     */
    private String expressBird;


}

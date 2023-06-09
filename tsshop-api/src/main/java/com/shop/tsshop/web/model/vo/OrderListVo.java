package com.shop.tsshop.web.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.shop.tsshop.web.model.domain.AddressOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderListVo {

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单价格
     */
    private BigDecimal amount;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * (0, "待付款"),(1, "待发货"),(3, "已完成"),(4, "待收货"),(-1, "已取消");
     */
    private Integer orderStatus;



    private Date createTime;
    private String goodsName;
    private String src;
    private String sku;
}

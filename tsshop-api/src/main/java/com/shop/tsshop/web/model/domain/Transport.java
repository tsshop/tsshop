package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("transport")
public class Transport implements Serializable {
    private static final long serialVersionUID = 1876655654053364580L;
    /**
     * 运费模板id
     */
    @TableId
    //"运费模板id",required=true)
    private Long transportId;

    /**
     * 运费模板名称
     */

    //"运费模板名称",required=true)
    private String transName;

    /**
     * 创建时间
     */

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //"创建时间",required=true)
    private Date createTime;

    /**
     * 店铺id
     */

    //"店铺id",required=true)
    private Long shopId;

    /**
     * 参考 TransportChargeType
     * 收费方式（0 按件数,1 按重量 2 按体积）
     */
    //"收费方式（0 按件数,1 按重量 2 按体积）",required=true)
    private Integer chargeType;


   /**
    * 是否包邮 0:不包邮 1:包邮
    */
    //"是否包邮 0:不包邮 1:包邮",required=true)
    private Integer isFreeFee;

    /**
     * 是否含有包邮条件
     */
    //"是否含有包邮条件",required=true)
    private Integer hasFreeCondition;

    /**
     * 指定条件包邮项
     */
    @TableField(exist=false)
    //"指定条件包邮项",required=true)
    private List<TransfeeFree> transfeeFrees;

    /**
     * 运费项
     */
    @TableField(exist=false)
    //"运费项",required=true)
    private List<Transfee> transfees;

}
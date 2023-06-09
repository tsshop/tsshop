package com.ts.shop.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Transport implements Serializable {
    private static final long serialVersionUID = 1876655654053364580L;
    /**
     * 运费模板id
     */

    private Long transportId;

    /**
     * 运费模板名称
     */

    private String transName;

    /**
     * 创建时间
     */

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 参考 TransportChargeType
     * 收费方式（0 按件数,1 按重量 2 按体积）
     */
    private Integer chargeType;


   /**
    * 是否包邮 0:不包邮 1:包邮
    */
    private Integer isFreeFee;

    /**
     * 是否含有包邮条件
     */
    private Integer hasFreeCondition;

    /**
     * 指定条件包邮项
     */

    private List<TransfeeFree> transfeeFrees;

    /**
     * 运费项
     */
    private List<Transfee> transfees;

}

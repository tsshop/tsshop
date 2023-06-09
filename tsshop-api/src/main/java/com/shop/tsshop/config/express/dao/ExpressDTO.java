package com.shop.tsshop.config.express.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: fuhuilei
 * @Date: 2022-04-30 11:44
 **/
@Data
public class ExpressDTO {

    //"增值物流状态： 1-已揽收， 2-在途中， 201-到达派件城市， 202-派件中， 211-已放入快递柜或驿站， 3-已签收， 311-已取出快递柜或驿站， 4-问题件， 401-发货无信息， 402-超时未签收， 403-超时未更新， 404-拒收（退件）， 412-快递柜或驿站超时未取")
    @JsonProperty("StateEx")
    private String StateEx;

    //"物流运单号")
    @JsonProperty("LogisticCode")
    private String LogisticCode;

    //"快递公司编码")
    @JsonProperty("ShipperCode")
    private String ShipperCode;

    //"物流状态：2-在途中,3-签收,4-问题件")
    @JsonProperty("State")
    private String State;

    //"订单编号")
    @JsonProperty("OrderCode")
    private String OrderCode;

    //"用户ID")
    @JsonProperty("EBusinessID")
    private String EBusinessID;

    //"快递收件人")
    @JsonProperty("DeliveryMan")
    private String DeliveryMan;

    //"快递收件号码")
    @JsonProperty("DeliveryManTel")
    private String DeliveryManTel;

    //"成功与否true/false")
    @JsonProperty("Success")
    private String Success;

    //"所在城市")
    @JsonProperty("Location")
    private String Location;

    //"快递公司名称")
    private String ShipperName;

    //"信息反馈")
    @JsonProperty("Reason")
    private String Reason;

    //"订单状态")
    private String orderStatus;

    //"快递物流信息")
    @JsonProperty("Traces")
    private List<Traces> Traces;
}

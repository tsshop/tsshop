/**
 * Copyright 2020 bejson.com
 */package com.ts.shop.domain.express.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Auto-generated: 2020-07-19
 *
 */
@Data
public class Traces {

   //"描述")
    @JsonProperty("AcceptStation")
    private String AcceptStation;

   //"时间")
    @JsonProperty("AcceptTime")
    private String AcceptTime;

   //"当前状态")
    @JsonProperty("Action")
    private String Action;

   //"当前城市")
    @JsonProperty("Location")
    private String Location;

}

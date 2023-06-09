package com.ts.shop.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class SeckillVo {

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateEndTime;

    private String title;

    /** 时间间隔 */
    private String timeInterval;

    /** 状态 */
    private Integer status;
    private Map<String, List<SeckillGoodsVo>> timeGoodsList;
}

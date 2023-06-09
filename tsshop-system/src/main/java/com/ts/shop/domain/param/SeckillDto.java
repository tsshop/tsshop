package com.ts.shop.domain.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ts.common.annotation.Excel;
import com.ts.shop.domain.SeckillGoods;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class SeckillDto {

    private Long id;
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateEndTime;

    private String title;

    /** 时间间隔 */
    private String timeInterval;

    /** 状态 */
    private Integer status;
    private Map<String,List<SeckillGoods>> timeGoodsList;
//    private List<SeckillGoods> goodsList;
}

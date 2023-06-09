package com.ts.shop.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MonthlyOrderListVo {
    private Integer num;
    @JsonFormat(pattern = "MM-dd")
    private Date time;
}

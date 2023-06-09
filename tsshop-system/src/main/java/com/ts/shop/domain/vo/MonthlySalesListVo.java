package com.ts.shop.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MonthlySalesListVo {
    private Double amt;
    @JsonFormat(pattern = "MM-dd")
    private Date time;
}

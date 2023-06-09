package com.ts.shop.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 秒杀活动对象 seckill
 * 
 * @author xu
 * @date 2023-02-21
 */
@Data
public class Seckill extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 秒杀开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "秒杀开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 秒杀结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "秒杀结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 修改截至时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "修改截至时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateEndTime;

    /** 活动标题 */
    @Excel(name = "活动标题")
    private String title;

    /** 是否关闭 */
    @Excel(name = "是否关闭")
    private Integer isDel;

    /** 时间间隔 */
    @Excel(name = "时间间隔")
    private String timeInterval;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;



}

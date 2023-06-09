package com.shop.tsshop.web.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : tsshop
 * @date : 2022/12/5
 */
@Data
public class TencentCallbackDto implements Serializable {

    /**
     * 事件组 ID
     * 1.房间事件组
     * 2.媒体事件组
     */
    private Integer EventGroupId;

    //事件ID
    private Integer EventType;

    //回调时间
    private Long CallbackTs;

    private EventInfo EventInfo;
}

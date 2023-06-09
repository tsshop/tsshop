package com.shop.tsshop.web.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : tsshop
 * @date : 2022/12/5
 */
@Data
public class EventInfo implements Serializable {

    //数字房间号
    private String RoomId;

    //事件发生时间秒级
    private Long EventTs;

    //事件发生时间毫秒级
    private Long EventMsTs;

    //用户ID
    private String UserId;

    //唯一标识符
    private Long UniqueId;

    //用户角色
    private Integer Role;

    //终端类型
    private Integer TerminalType;

    //用户类型
    private Integer UserType;

    //进房原因
    private Integer Reason;

    private String TaskId;
}

package com.shop.tsshop.web.netty.protocol.response;

import com.shop.tsshop.web.netty.protocol.request.BaseMessage;
import lombok.Data;

/**
 * @Desc : 单聊响应给对方的消息体
 * @Create : tsshop ~ 2020/06/14
 */
@Data
public class MsgBoxResponseMessage extends BaseMessage {

    /** 消息未读数量 */
    private long unreadCount;
    private String id;
}

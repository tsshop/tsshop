package com.shop.tsshop.web.netty.protocol.response;

import com.shop.tsshop.web.netty.protocol.request.BaseMessage;
import lombok.Data;

/**
 * @Desc : 单聊响应给对方的消息体
 * @Create : tsshop ~ 2020/06/14
 */
@Data
public class OnlineStatusChangeResponseMessage extends BaseMessage {

    /** 好友ID */
    private String friendId;
    /** 在线状态 */
    private String onlineStatus;

}

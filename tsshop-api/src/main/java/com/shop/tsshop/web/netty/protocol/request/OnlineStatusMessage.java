package com.shop.tsshop.web.netty.protocol.request;

import lombok.Data;

/**
 * @Desc : 单聊消息体
 * @Create : tsshop ~ 2020/06/14
 */
@Data
public class OnlineStatusMessage extends BaseMessage {

    private String status;

}

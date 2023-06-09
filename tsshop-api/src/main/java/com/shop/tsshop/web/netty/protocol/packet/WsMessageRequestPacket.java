package com.shop.tsshop.web.netty.protocol.packet;

import com.shop.tsshop.web.model.domain.User;
import lombok.Data;

/**
 * @Desc : 消息传输体
 * @Create : tsshop ~ 2020/06/13
 */
@Data
public class WsMessageRequestPacket {

    /** 命令 */
    private Integer cmd;
    /** 消息体 */
    private String message;

    private String token;

    private User user;
}

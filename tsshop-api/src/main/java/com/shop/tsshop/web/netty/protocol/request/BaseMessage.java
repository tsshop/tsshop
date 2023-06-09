package com.shop.tsshop.web.netty.protocol.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc : 基础消息体
 * @Create : tsshop ~ 2020/06/14
 */
@Data
public abstract class BaseMessage implements Serializable {
    /** 命令 */
    private Integer cmd;
    private String push_clientid;
}

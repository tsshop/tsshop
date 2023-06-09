package com.shop.tsshop.web.netty.protocol.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc :
 * @Create : tsshop ~ 2020/06/14
 */
@Data
public class Mine implements Serializable {
    private String avatarUrl;
    private String id;
    private String name;
    //是否我发送的消息
    private boolean mine;
    private String content;
    //关联内容id
    private Long relationId;
}

package com.shop.tsshop.web.netty.protocol.request;

import lombok.Data;

/**
 * @author : tsshop
 * @date : 2023/5/24
 */
@Data
public class LiveMessageBody {

    private Long roomId;

    private String content;
}

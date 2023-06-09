package com.shop.tsshop.config.token;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
public class JwtUserInfo {
    /**
     * 用户id
     */
    private Long userId;
}

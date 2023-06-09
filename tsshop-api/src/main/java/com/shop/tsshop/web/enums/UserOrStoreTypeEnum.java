package com.shop.tsshop.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author sunyawei
 */

@Getter
@AllArgsConstructor
public enum UserOrStoreTypeEnum {
    /**
     * 用户
     */
    USER(1, "用户"),
    /**
     * 商家
     */
    STORE(2, "商家");
    private int status;
    private String msg;

    public static UserOrStoreTypeEnum toType(int status) {
        return Stream.of(UserOrStoreTypeEnum.values())
                .filter(p -> p.status == status)
                .findAny()
                .orElse(null);
    }
}

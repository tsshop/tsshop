package com.ts.shop.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName NumberEnmus
 * @Author TS SHOP
 * @create 2023/5/23
 */
@Getter
@AllArgsConstructor
public enum NumberEnmus {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    MinusOne(-1);
    private Integer number;
}

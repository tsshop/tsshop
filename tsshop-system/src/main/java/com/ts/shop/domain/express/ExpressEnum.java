package com.ts.shop.domain.express;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum ExpressEnum {

    SF("SF", "顺丰速运"),
    HTKY("HTKY", "百世快递"),
    ZTO("ZTO", "中通快递"),
    STO("STO", "申通快递"),
    YTO("YTO", "圆通速递"),
    YD("YD", "韵达速递"),
    YZPY("YZPY", "邮政快递包裹"),
    JD("JD", "京东快递"),
    UC("UC", "优速快递"),
    DBL("DBL", "德邦快递"),
    JTSD("JTSD", "极兔速递"),
    ZYE("ZYE", "众邮快递"),
    ZJS("ZJS", "宅急送");


    private String code;
    private String msg;

    public static ExpressEnum toType(String value) {
        return Stream.of(ExpressEnum.values())
                .filter(p -> p.code.equals(value))
                .findAny()
                .orElse(null);
    }

    public static String getMsg(String status) {
        for (ExpressEnum c : ExpressEnum.values()) {
            if (c.code.equals(status)) {
                return c.msg;
            }
        }
        return null;
    }
}

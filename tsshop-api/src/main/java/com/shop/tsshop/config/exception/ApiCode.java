package com.shop.tsshop.config.exception;

public enum ApiCode {
    SUCCESS(200, "操作成功"),
    PARAMETER(400, "参数错误"),
    UNAUTHORIZED(401, "非法访问"),
    NOT_PERMISSION(403, "没有权限"),
    TOO_LONG(502, "最多只能有五个"),

    LOGIN_TIME_OUT(4006, "登录超时"),
    NO_USER(600,"用户不存在,请注册登录"),
    FAIL(500, "操作失败"),

    OUT_OF_STOCK(501, "订单有商品库存不足,是否继续下单？"),
    NONAME(5007, "请填写12字以内的姓名"),
    NOPHONE(5008, "请填写合法的手机号");


    private final int code;
    private final String msg;

    ApiCode(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiCode getApiCode(int code) {
        ApiCode[] ecs = ApiCode.values();
        for (ApiCode ec : ecs) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

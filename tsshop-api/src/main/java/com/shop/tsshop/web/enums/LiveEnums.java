package com.shop.tsshop.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : tsshop
 * @date : 2022/12/19
 */
public class LiveEnums {

    @Getter
    @AllArgsConstructor
    public enum LiveStatus{
        NOT_STARTED(1,"未开始"),
        LIVE_BROADCAST(2,"直播中"),
        END_OF_LIVE(3,"直播结束"),
        NOT_STARTED_END(4,"已失效")
        ;
        private Integer code;
        private String msg;
    }

    @Getter
    @AllArgsConstructor
    public enum LiveRole{
        MEMBER_TRTC_ANCHOR(20,"主播"),
        MEMBER_TRTC_VIEWER(21,"观众"),
        ;
        private Integer code;
        private String msg;
    }

    @Getter
    @AllArgsConstructor
    public enum LiveLinkStatus{
        ONE(1,"等待"),
        TWO(2,"连麦中"),
        THREE(3,"已结束"),
        FOUR(4,"已拒绝");
        private Integer code;
        private String msg;
    }
}

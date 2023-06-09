package com.shop.tsshop.web.netty.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author tsshop
 * @since 2021/5/30
 */
public class MsgBoxEvent extends ApplicationEvent {
    public MsgBoxEvent(Object source) {
        super(source);
    }
}

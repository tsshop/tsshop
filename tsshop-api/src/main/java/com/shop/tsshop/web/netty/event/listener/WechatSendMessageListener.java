package com.shop.tsshop.web.netty.event.listener;


import com.shop.tsshop.web.netty.event.UnReadMessageEvent;
import com.shop.tsshop.web.netty.protocol.packet.WsMessageRequestPacket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 监听未读消息事件，发送微信消息
 */
@Slf4j
@Component
public class WechatSendMessageListener implements ApplicationListener<UnReadMessageEvent> {

    @Override
    public void onApplicationEvent(UnReadMessageEvent source) {
        try {
            // 监听函数中 务必保证不出异常，如果出现异常，websocket会断开链接
            WsMessageRequestPacket packet = (WsMessageRequestPacket)source.getSource();
            log.info("【wechat】发送一条未读消息,{}",packet.getMessage());
        }catch (Exception e){ }
    }
}

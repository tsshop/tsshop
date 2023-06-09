package com.shop.tsshop.web.netty.event.listener;


import com.shop.tsshop.web.netty.domain.MsgBoxUser;
import com.shop.tsshop.web.netty.event.MsgBoxEvent;
import com.shop.tsshop.web.netty.protocol.command.MsgCommand;
import com.shop.tsshop.web.netty.protocol.response.MsgBoxResponseMessage;
import com.shop.tsshop.web.netty.utils.ObjectMapperUtils;
import com.shop.tsshop.web.netty.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 刷新请求
 * @author tsshop
 * @since 2021/5/30
 */
@Slf4j
@Component
public class MsgBoxEventListener implements ApplicationListener<MsgBoxEvent> {

    @Override
    public void onApplicationEvent(MsgBoxEvent msgBoxEvent) {
        MsgBoxUser source = (MsgBoxUser)msgBoxEvent.getSource();
        Channel channel = SessionUtil.getChannel(source.getUserId());
        if (channel == null) {
            // 不在线
            return;
        }
        MsgBoxResponseMessage resp = new MsgBoxResponseMessage();
        resp.setCmd(MsgCommand.MSG_BOX.getCmd());
        resp.setUnreadCount(1);
        channel.writeAndFlush(new TextWebSocketFrame(ObjectMapperUtils.toJsonString(resp)).retain());
    }
}

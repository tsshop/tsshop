package com.shop.tsshop.web.netty.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.shop.tsshop.web.netty.attribute.Attributes;
import com.shop.tsshop.web.netty.protocol.request.BaseMessage;
import com.shop.tsshop.web.netty.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc :
 * @Create : tsshop ~ 2020/06/13
 */
@Slf4j
public class SessionUtil {

    // userId -> Channel
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    // groupId -> ChannelGroup
    private static final Map<String, ChannelGroup> groupIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Channel channel, Session session){
        userIdChannelMap.put(session.getId(),channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unbindSession(Channel channel){
        if (hasLogin(channel)){
            Session session = getSession(channel);
            if(session!=null){
                userIdChannelMap.remove(session.getId());
                channel.attr(Attributes.SESSION).set(null);
                log.info("[{}]离线",session.getName());
            }
        }
    }

    public static Session getSession(Channel channel){
        return channel.attr(Attributes.SESSION).get();
    }

    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }


    public static void bindChannelGroup(String groupId,ChannelGroup channelGroup){
        groupIdChannelMap.put(groupId,channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId){
        return groupIdChannelMap.get(groupId);
    }

    // 测试方法,记得删除
    @Deprecated
    public static ChannelGroup initChannelGroup(String groupId,ChannelGroup channelGroup){
        userIdChannelMap.keySet().forEach(item -> channelGroup.add(userIdChannelMap.get(item)));
        groupIdChannelMap.put(groupId,channelGroup);
        return channelGroup;
    }

    public static void sendMsg(String userId, BaseMessage baseMessage){
        String message=ObjectMapperUtils.toJsonString(baseMessage);
        Channel channel=userIdChannelMap.get(userId);
        if(channel==null){
            push(baseMessage);
            return;
        }
        channel.writeAndFlush(new TextWebSocketFrame(message).retain());
    }
    public static String push(BaseMessage baseMessage){
        String url="https://ee7290a6-32cb-4f46-83a3-4e6d831ca3c6.bspapp.com/push";
        System.out.println("--------------");
        return  HttpUtil.post(url, JSON.toJSONString(baseMessage));
    }
    public static void sendMsg(String userId, String message){
        Channel channel=userIdChannelMap.get(userId);
        if(channel==null){
            return;
        }
        channel.writeAndFlush(new TextWebSocketFrame(message).retain());
    }
    public static void sendAll(BaseMessage baseMessage){
        String message=ObjectMapperUtils.toJsonString(baseMessage);
        for (Channel channel : userIdChannelMap.values()) {
            if(channel==null){
                return;
            }
            channel.writeAndFlush(new TextWebSocketFrame(message).retain());
        }
    }


    public static int getOnlineCount(){
        return userIdChannelMap.keySet().size();
    }
    public static int getOnlineGroupCount(){
        return groupIdChannelMap.keySet().size();
    }
}

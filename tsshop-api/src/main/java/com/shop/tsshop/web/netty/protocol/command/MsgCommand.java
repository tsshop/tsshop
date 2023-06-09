package com.shop.tsshop.web.netty.protocol.command;

/**
 * @Desc :
 * @Create : tsshop ~ 2020/06/14
 */
public enum MsgCommand {
    /** 第一次(或重连)初始化连接 */
    CONNECT(1)
    /** 聊天消息 */
    ,CHAT(2)
    /** 消息签收 */
    ,SIGNED(3)
    /** 客户端保持心跳 */
    ,KEEPALIVE(4)
    /** 群消息 */
    ,GROUP_MSG(5)
    /** 在线状态 */
    ,ONLINE_STATUS(6)
    /** 消息盒子 */
    ,MSG_BOX( 7)
    ,CS_MSG(8)
    ,CS_ONLINE_STATUS(9)
    /** 已读回执 */
    ,READ_FLAG(10)
    /** 撤回消息 */
    ,WITHDRAW(11)
    /** 敏感词 */
    ,SENSITIVE_WORD(12)
    /** 删除消息 */
    ,DEL_MSG(13)
    /** 刷新消息列表 */
    ,NEW_MSG(14)

    /** 好友申请 */
    ,FRIEND_APPLY( 101)
    /** 好友删除 */
    ,FRIEND_DEL( 102)//201
    /** 好友添加 */
    ,FRIEND_ADD( 103)

    /** 群组申请 */
    ,GROUP_APPLY(201)//
    /** 群组删除 */
    ,GROUP_DEL( 202)
    /** 群组添加 */
    ,GROUP_ADD( 203)

    /** 系统消息提醒 */
    ,SYSTEM_MSG( 301)

    /** 创建房间 */
    ,CALL_GROUP(501)
    /** 状态变更 */
    ,ROOM_STATUS(502)
    /** 禁言状态 */
    ,TABOO_STATUS(503)
    /** 闭麦状态 */
    ,CLOSED_STATUS(504)

    /** 开始直播 */
    ,LIVE_START(601)
    /** 结束直播 */
    ,LIVE_END(602)
    /** 送出礼物 */
    ,LIVE_GIVE_GIFT(603)
    /** 观众进房/退房 人数变更 */
    ,LIVE_ROOM_STATUS(604)
    /** 直播弹幕 */
    ,LIVE_MESSAGE(605)
    /** 切换商品 */
    ,EXPLAIN_GOODS(606)
    ,LIVE_PRAISE(607)
    ,LIVE_LINK(608)
    ,LIVE_LINK_APPLY(609)

    /** 等待接听 */
    ,CALL_SINGLE(701)
    /** 结束通话 */
    ,CALL_END_SINGLE(702)
    /** 拒接 */
    ,REFUSE_CALL(703)

    /** 等待接听 */
    ,VIDEO_SINGLE(801)
    /** 结束通话 */
    ,VIDEO_END_SINGLE(802)
    /** 拒接 */
    ,REFUSE_VIDEO(803)
    ;

    private final Integer cmd;

    MsgCommand(Integer cmd) {
        this.cmd = cmd;
    }

    public Integer getCmd(){
        return cmd;
    }

}

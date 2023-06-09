package com.shop.tsshop.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : tsshop
 * @date : 2022/12/5
 */
public class TencentEnums {

    /**
     * 腾讯云回调事件
     */
    @Getter
    @AllArgsConstructor
    public enum TencentCallbackEnum {
        EVENT_TYPE_CREATE_ROOM(101,"创建房间"),
        EVENT_TYPE_DISMISS_ROOM(102,"解散房间"),
        EVENT_TYPE_ENTER_ROOM(103,"进入房间"),
        EVENT_TYPE_EXIT_ROOM(104,"退出房间"),
        EVENT_TYPE_CHANGE_ROLE(105,"切换角色"),
        EVENT_TYPE_START_VIDEO(201,"开始推送视频数据"),
        EVENT_TYPE_STOP_VIDEO(202,"停止推送视频数据"),
        EVENT_TYPE_START_AUDIO(203,"开始推送音频数据"),
        EVENT_TYPE_STOP_AUDIO(204,"停止推送音频数据"),
        EVENT_TYPE_START_ASSIT(205,"开始推送辅路数据"),
        EVENT_TYPE_STOP_ASSIT(206,"停止推送辅路数据"),
        EVENT_TYPE_CLOUD_RECORDING_RECORDER_START(301,"云端录制录制模块启动"),
        EVENT_TYPE_CLOUD_RECORDING_RECORDER_STOP(302,"云端录制录制模块退出"),
        EVENT_TYPE_CLOUD_RECORDING_FAILOVER(303,"云端录制发生迁移，原有的录制任务被迁移到新负载上时触发"),
        EVENT_TYPE_CLOUD_RECORDING_DOWNLOAD_IMAGE_ERROR(309,"云端录制下载解码图片文件发生错误"),
        EVENT_TYPE_CLOUD_RECORDING_VOD_COMMIT(311,"云端录制 VOD 录制任务上传媒体资源完成"),
        EVENT_TYPE_CLOUD_RECORDING_VOD_STOP(312,"云端录制 VOD 录制任务结束"),
        ;

        private Integer code;
        private String msg;

        public static TencentCallbackEnum getByCode(Integer code) {
            for(TencentCallbackEnum e : TencentCallbackEnum.values()){
                if(e.code.equals(code)){
                    return e;
                }
            }
            return null;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum CallStatus{
        NOT_STARTED(1,"未开始"),
        ON_CALL(2,"通话中"),
        END_OF_CALL(3,"通话结束"),
        NOT_STARTED_END(4,"异常结束");
        private Integer code;
        private String msg;
    }

    @Getter
    @AllArgsConstructor
    public enum CallUserStatus{
        NOT_JOINED(1,"未加入"),
        ON_CALL(2,"通话中"),
        END_OF_CALL(3,"通话结束");
        private Integer code;
        private String msg;
    }
}

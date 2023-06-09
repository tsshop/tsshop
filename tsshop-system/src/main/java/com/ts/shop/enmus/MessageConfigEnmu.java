package com.ts.shop.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName MessageConfigEnmu
 * @Author TS SHOP
 * @create 2023/6/6
 */
@Getter
@AllArgsConstructor
public enum MessageConfigEnmu {
    // 云极短信开启状态
    YUNJI_OPEN_STATUS("YUNJI_OPEN_STATUS","open_status"),
    YUNJI_ACCESSKEY("YUNJI_ACCESSKEY","accessKey"),
    YUNJI_ACCESSSECRET("YUNJI_ACCESSSECRET","accessSecret"),
    YUNJI_SIGNCODE("YUNJI_SIGNCODE","signCode"),
    YUNJI_TEMPLATECODE("YUNJI_TEMPLATECODE","templateCode"),
    YUNJI_CLASSIFICATIONSECRET("YUNJI_CLASSIFICATIONSECRET","classificationSecret"),
    // 大于短信服务开启状态
    ALI_DAYU_OPEN_STATUS("ALI_DAYU_OPEN_STATUS","ali_dayu_open_status"),

    ALI_DAYU_ACCESSKEYID("ALI_DAYU_ACCESSKEYID","ali_dayu_accesskeyid"),

    ALI_DAYU_ACCESSKEYSECRET("ALI_DAYU_ACCESSKEYSECRET","ali_dayu_accesskeysecret"),

    ALI_DAYU_SIGNNAME("ALI_DAYU_SIGNNAME","ali_dayu_signname"),

    ALI_DAYU_TEMPLATECODE("ALI_DAYU_TEMPLATECODE","ali_dayu_templatecode");

    private String code;
    private String msg;
}

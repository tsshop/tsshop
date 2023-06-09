package com.ts.shop.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName AliOssConfigEnum
 * @Author TS SHOP
 * @create 2023/6/6
 */
@Getter
@AllArgsConstructor
public enum AliOssConfigEnmu {
        ALI_OSS_ENDPOINT("ALI_OSS_ENDPOINT","endpoint"),
        ALI_OSS_ENDPOINTECS("ALI_OSS_ENDPOINTECS","endpointECS"),
        ALI_OSS_ACCESSKEYID("ALI_OSS_ACCESSKEYID","accessKeyId"),
        ALI_OSS_ACCESSKEYSECRET("ALI_OSS_ACCESSKEYSECRET","accessKeySecret"),
        ALI_OSS_BUCKETNAME("ALI_OSS_BUCKETNAME","bucketName"),
        ALI_OSS_FILEDIR("ALI_OSS_FILEDIR","filedir"),
        ALI_OSS_EXPIRETIME("ALI_OSS_EXPIRETIME","expireTime"),
        ALI_OSS_MAXFILESIZE("ALI_OSS_MAXFILESIZE","maxFileSize"),
        ALI_OSS_DOMAIN("ALI_OSS_DOMAIN","domain");

        private String code;
        private String msg;
}

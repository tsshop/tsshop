package com.ts.shop.domain.vo;

import com.ts.common.annotation.Excel;
import com.ts.shop.domain.LiveStore;
import lombok.Data;

/**
 * @ClassName LiveStoreInfoVo
 * @Author TS SHOP
 * @create 2023/5/29
 */
@Data
public class LiveStoreInfoVo extends LiveStore {
    /** 负责人姓名 */
    private String managerName;

    /** 负责人手机号 */
    private String managerPhone;

    /** 负责人身份证号 */
    private String managerIdCard;

    /** 店铺类型  0 个人 1 商户 */
    private Integer liveStoreType;

    /** 身份证正面照片 */
    private String idCardObverse;

    /** 身份证反面照片 */
    private String idCardReverse;

    /** 营业执照 */
    private String businessLicense;

    /** 企业名称 */
    private String enterpriseName;

    /** 企业统一信用代码 */
    private String enterpriseCreditCode;
}

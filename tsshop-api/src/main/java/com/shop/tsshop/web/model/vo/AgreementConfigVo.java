package com.shop.tsshop.web.model.vo;

import lombok.Data;

/**
 * @ClassName AgreementConfigVo
 * @Author TS SHOP
 * @create 2023/6/9
 */
@Data
public class AgreementConfigVo {
    private String serviceTitle;
    private String privacyTitle;
    private String payTitle;
    private String serviceAgreement;
    private String privacyAgreement;
    private String payAgreement;
}

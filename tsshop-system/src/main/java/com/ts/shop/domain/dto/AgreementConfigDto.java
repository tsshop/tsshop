package com.ts.shop.domain.dto;

import lombok.Data;

/**
 * @ClassName AgreementConfigDto
 * @Author TS SHOP
 * @create 2023/6/9
 */
@Data
public class AgreementConfigDto {
    private String serviceTitle;
    private String privacyTitle;
    private String payTitle;
    private String serviceAgreement;
    private String privacyAgreement;
    private String payAgreement;
}

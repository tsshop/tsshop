package com.ts.shop.domain.param;

import lombok.Data;

@Data
public class ReturnParam {
//    @NotBlank
    private Long id;

    /** 真实退款金额 */
    private Double reallyAmt;

    /** 退货地址 */
    private Long addressId;


//    @NotBlank
    /** 1:成功 2：审核拒绝*/
    private Integer status;


    /** 拒绝理由 */
    private String refuseDetail;
}

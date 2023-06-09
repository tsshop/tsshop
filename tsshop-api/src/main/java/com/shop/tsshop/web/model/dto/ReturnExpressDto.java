package com.shop.tsshop.web.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 退款表
 * </p>
 *
 * @author fuhuilei
 * @since 2022-09-26
 */
@Data
public class ReturnExpressDto implements Serializable {


    /**
     * id
     */
    @NotBlank(message = "id不能为空")
    private Long id;

    /**
     * 物流单号
     */
    @NotBlank(message = "物流单号不能为空")
    private String expressNo;

    /**
     * 物流编码
     */
    @NotBlank(message = "物流编码不能为空")
    private String express;

    /**
     * 物流公司名字
     */
    @NotBlank(message = "物流名字不能为空")
    private String expressName;


}

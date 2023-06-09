package com.shop.tsshop.web.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : tsshop
 * @date : 2023/5/26
 */
@Data
@Accessors(chain = true)
public class LiveUser implements Serializable {

    private Long id;

    private String name;

    private String avatarUrl;
}

package com.shop.tsshop.web.model.vo;

import com.shop.tsshop.web.model.domain.LiveStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName liveStoreDetialVo
 * @Author TS SHOP
 * @create 2023/5/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveStoreDetialVo {
    /**
     * 店铺申请标志
     */
    private Boolean applyFlag;
    /**
     * 失败原因
     */
    private String failReason;
    /**
     * 店铺申请状态
     */
    private Integer applyStatus;
    /**
     * 小店信息
     */
    private LiveStore liveStore;
}

package com.ts.shop.controller;

import com.ts.common.core.domain.AjaxResult;
import com.ts.common.core.page.TableDataInfo;
import com.ts.shop.domain.dto.LiveGiftOrderListDto;
import com.ts.shop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName LiveGiftOrderController
 * @Author TS SHOP
 * @create 2023/5/31
 */
@RestController
@RequestMapping("/liveGift/order")
public class LiveGiftOrderController {
    @Autowired
    IOrderService orderService;

    @PreAuthorize("@ss.hasPermi('liveGift:order:list')")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody LiveGiftOrderListDto liveGiftOrderListDto) {
        return orderService.getLiveGiftOrderList(liveGiftOrderListDto);
    }

}

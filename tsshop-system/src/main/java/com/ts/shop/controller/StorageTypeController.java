package com.ts.shop.controller;

import com.ts.common.core.domain.AjaxResult;
import com.ts.shop.domain.vo.StorageTypeVo;
import com.ts.shop.enmus.StorageTypeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ClassName StorageTypeController
 * @Author TS SHOP
 * @create 2023/6/6
 */
@RestController
@RequestMapping("/storage/type")
public class StorageTypeController {


    @GetMapping("/getType")
    public AjaxResult getType () {
        List<StorageTypeVo> storageTypeVoList = new ArrayList<>();
        Arrays.stream(StorageTypeEnum.values()).forEach(item -> {
            StorageTypeVo storageTypeVo = new StorageTypeVo();
            storageTypeVo.setCode(item.getCode());
            storageTypeVo.setMsg(item.getMsg());
            storageTypeVo.setLocation(item.getLocation());
            storageTypeVoList.add(storageTypeVo);
        });
        return AjaxResult.success(storageTypeVoList);
    }
}

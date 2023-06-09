package com.shop.tsshop.web.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.enums.StorageTypeEnum;
import com.shop.tsshop.web.model.domain.TsshopConfigStorage;
import com.shop.tsshop.web.util.LocalStorageUtils;
import com.shop.tsshop.web.util.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private OssUtil ossUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LocalStorageUtils localStorageUtils;

    @PostMapping("/uploadFile")
    @PassToken
    public ApiResult<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
        String tsshopConfigStorage = (String) redisTemplate.opsForValue().get("config:storage");
        if (ObjectUtil.isNull(tsshopConfigStorage)){
            return ApiResult.fail("存储配置信息有误请，前往后台配置");
        }
        TsshopConfigStorage config = JSONUtil.toBean(tsshopConfigStorage,TsshopConfigStorage.class);
        if (StorageTypeEnum.ALI_OSS.getCode().equals(config.getStorageType())){
            try{
                return ApiResult.ok(ossUtil.uploadFile(config,file,"shop/",0));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (StorageTypeEnum.LOCAL_STORAGE.getCode().equals(config.getStorageType())){
            try{
                return ApiResult.ok(localStorageUtils.uploadFile(file));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ApiResult.fail("上传失败");

    }
}

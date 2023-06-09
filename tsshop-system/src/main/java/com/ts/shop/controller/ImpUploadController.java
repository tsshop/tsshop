package com.ts.shop.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.ts.common.core.domain.AjaxResult;
import com.ts.shop.domain.TsshopConfigStorage;
import com.ts.shop.enmus.StorageTypeEnum;
import com.ts.shop.util.LocalStorageUtils;
import com.ts.shop.util.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author tiantian
 * @since 2021/3/4
 * 图片上传请求
 */
@RestController
@RequestMapping("api/imgUpload")
public class ImpUploadController {
    @Autowired
    private OssUtil ossUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LocalStorageUtils localStorageUtils;

    /**
     * 单个图片上传
     */
    @PostMapping("/one")
    public AjaxResult imgUpload(@RequestParam(value = "img", required = false) MultipartFile file) {
        TsshopConfigStorage config = getConfig();
        if (StorageTypeEnum.ALI_OSS.getCode().equals(config.getStorageType())){
            try{
                String imgUrl = ossUtil.checkImage(config,file);
                return AjaxResult.success(imgUrl);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (StorageTypeEnum.LOCAL_STORAGE.getCode().equals(config.getStorageType())){
            try{
                String imgUrl = localStorageUtils.uploadFile(file);
                return AjaxResult.success(imgUrl);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return new AjaxResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "上传失败");
    }

    /**
     * 多个图片上传
     */
    @PostMapping("/list")
    public AjaxResult imgUploadList(@RequestParam(value = "imgList", required = false) List<MultipartFile> fileList) {
        TsshopConfigStorage config = getConfig();
        if (ObjectUtil.isNull(config)){
            return AjaxResult.error("存储配置信息有误请，前往后台配置");
        }
        if (StorageTypeEnum.ALI_OSS.getCode().equals(config.getStorageType())){
            try{
                String imgUrl = ossUtil.checkList(config,fileList);
                return AjaxResult.success(imgUrl);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (StorageTypeEnum.LOCAL_STORAGE.getCode().equals(config.getStorageType())){
            try{
                String imgUrl = localStorageUtils.uploadFileList(fileList);
                return AjaxResult.success(imgUrl);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return new AjaxResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "上传失败");
    }


    /**
     * 支付附件上传
     */
    @PostMapping("/payFile")
    public AjaxResult payFileUpload(@RequestParam(value = "file", required = false) MultipartFile file,String path) {
        TsshopConfigStorage config = getConfig();
        if (StorageTypeEnum.ALI_OSS.getCode().equals(config.getStorageType())){
            try{
                Map<String,String> result = ossUtil.payFileUpload(config,file,path);
                return AjaxResult.success(result);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (StorageTypeEnum.LOCAL_STORAGE.getCode().equals(config.getStorageType())){
            try{
                Map<String,String> result = localStorageUtils.payFileUpload(file,path);
                return AjaxResult.success(result);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return new AjaxResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "上传失败");
    }


    /**
     * 获取配置信息
     * @return                 存储配置
     */
    public TsshopConfigStorage getConfig(){
        String config = (String) redisTemplate.opsForValue().get("config:storage");
        if (ObjectUtil.isNull(config)){
            throw new RuntimeException("存储配置信息有误请，前往后台配置");
        }
        return JSONUtil.toBean(config, TsshopConfigStorage.class);
    }
}

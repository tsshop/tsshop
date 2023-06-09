package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ts.common.core.redis.RedisCache;
import com.ts.shop.domain.TsshopConfigStorage;
import com.ts.shop.mapper.TsshopConfigStorageMapper;
import com.ts.shop.service.ITsshopConfigStorageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ts.common.annotation.Log;
import com.ts.common.core.controller.BaseController;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.enums.BusinessType;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 存储配置Controller
 * 
 * @author ruoyi
 * @date 2023-06-06
 */
@RestController
@RequestMapping("/system/storage")
public class TsshopConfigStorageController extends BaseController
{
    @Autowired
    private ITsshopConfigStorageService tsshopConfigStorageService;

    @Autowired
    private TsshopConfigStorageMapper tsshopConfigStorageMapper;

    @Autowired
    RedisCache redisCache;

    /**
     * 查询存储配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:storage:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsshopConfigStorage tsshopConfigStorage)
    {
        startPage();
        List<TsshopConfigStorage> list = tsshopConfigStorageService.selectTsshopConfigStorageList(tsshopConfigStorage);
        return getDataTable(list);
    }

    /**
     * 导出存储配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:storage:export')")
    @Log(title = "存储配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TsshopConfigStorage tsshopConfigStorage)
    {
        List<TsshopConfigStorage> list = tsshopConfigStorageService.selectTsshopConfigStorageList(tsshopConfigStorage);
        ExcelUtil<TsshopConfigStorage> util = new ExcelUtil<TsshopConfigStorage>(TsshopConfigStorage.class);
        util.exportExcel(response, list, "存储配置数据");
    }

    /**
     * 获取存储配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:storage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tsshopConfigStorageService.selectTsshopConfigStorageById(id));
    }

    /**
     * 新增存储配置
     */
    @PreAuthorize("@ss.hasPermi('system:storage:add')")
    @Log(title = "存储配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsshopConfigStorage tsshopConfigStorage)
    {
        Long count = tsshopConfigStorageMapper.selectCount(new LambdaQueryWrapper<TsshopConfigStorage>().eq(TsshopConfigStorage::getOpenStatus, true));
        if (count > 1){
            return AjaxResult.error("只允许开启一个存储配置");
        }
        if (tsshopConfigStorage.getOpenStatus()){
            redisCache.setCacheObject("config:storage", JSON.toJSONString(tsshopConfigStorage));
        }
        return toAjax(tsshopConfigStorageService.insertTsshopConfigStorage(tsshopConfigStorage));
    }

    /**
     * 修改存储配置
     */
    @PreAuthorize("@ss.hasPermi('system:storage:edit')")
    @Log(title = "存储配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsshopConfigStorage tsshopConfigStorage)
    {
        Long count = tsshopConfigStorageMapper.selectCount(new LambdaQueryWrapper<TsshopConfigStorage>().eq(TsshopConfigStorage::getOpenStatus, true).ne(TsshopConfigStorage::getId,tsshopConfigStorage.getId()));
        if (count > 1){
            return AjaxResult.error("只允许开启一个存储配置");
        }
        if (tsshopConfigStorage.getOpenStatus()){
            redisCache.setCacheObject("config:storage", JSON.toJSONString(tsshopConfigStorage));
        }
        return toAjax(tsshopConfigStorageService.updateTsshopConfigStorage(tsshopConfigStorage));
    }

    /**
     * 删除存储配置
     */
    @PreAuthorize("@ss.hasPermi('system:storage:remove')")
    @Log(title = "存储配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsshopConfigStorageService.deleteTsshopConfigStorageByIds(ids));
    }
}

package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ts.shop.domain.LiveStoreWithdrawalsConfig;
import com.ts.shop.service.ILiveStoreWithdrawalsConfigService;
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
 * 店铺提现配置Controller
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
@RestController
@RequestMapping("/liveConfig/config")
public class LiveStoreWithdrawalsConfigController extends BaseController
{
    @Autowired
    private ILiveStoreWithdrawalsConfigService liveStoreWithdrawalsConfigService;

    @GetMapping("/accountType")
    public AjaxResult getAccountType() {
        return liveStoreWithdrawalsConfigService.getAccountType();
    }
    /**
     * 查询店铺提现配置列表
     */
    @PreAuthorize("@ss.hasPermi('liveConfig:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig)
    {
        startPage();
        List<LiveStoreWithdrawalsConfig> list = liveStoreWithdrawalsConfigService.selectLiveStoreWithdrawalsConfigList(liveStoreWithdrawalsConfig);
        return getDataTable(list);
    }

    /**
     * 导出店铺提现配置列表
     */
    @PreAuthorize("@ss.hasPermi('liveConfig:config:export')")
    @Log(title = "店铺提现配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig)
    {
        List<LiveStoreWithdrawalsConfig> list = liveStoreWithdrawalsConfigService.selectLiveStoreWithdrawalsConfigList(liveStoreWithdrawalsConfig);
        ExcelUtil<LiveStoreWithdrawalsConfig> util = new ExcelUtil<LiveStoreWithdrawalsConfig>(LiveStoreWithdrawalsConfig.class);
        util.exportExcel(response, list, "店铺提现配置数据");
    }

    /**
     * 获取店铺提现配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('liveConfig:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(liveStoreWithdrawalsConfigService.selectLiveStoreWithdrawalsConfigById(id));
    }

    /**
     * 新增店铺提现配置
     */
    @PreAuthorize("@ss.hasPermi('liveConfig:config:add')")
    @Log(title = "店铺提现配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig)
    {
        if (liveStoreWithdrawalsConfigService.selectLiveStoreWithdrawalsConfigList(new LiveStoreWithdrawalsConfig()).size() < 1){
            return toAjax(liveStoreWithdrawalsConfigService.insertLiveStoreWithdrawalsConfig(liveStoreWithdrawalsConfig));
        }
        return AjaxResult.error("配置信息只允许添加一条");
    }

    /**
     * 修改店铺提现配置
     */
    @PreAuthorize("@ss.hasPermi('liveConfig:config:edit')")
    @Log(title = "店铺提现配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig)
    {
        return toAjax(liveStoreWithdrawalsConfigService.updateLiveStoreWithdrawalsConfig(liveStoreWithdrawalsConfig));
    }

    /**
     * 删除店铺提现配置
     */
    @PreAuthorize("@ss.hasPermi('liveConfig:config:remove')")
    @Log(title = "店铺提现配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(liveStoreWithdrawalsConfigService.deleteLiveStoreWithdrawalsConfigByIds(ids));
    }
}

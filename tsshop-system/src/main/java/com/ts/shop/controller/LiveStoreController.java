package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ts.shop.domain.LiveStore;
import com.ts.shop.service.ILiveStoreService;
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
 * 直播小店Controller
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
@RestController
@RequestMapping("/system/store")
public class LiveStoreController extends BaseController
{
    @Autowired
    private ILiveStoreService liveStoreService;

    /**
     * 查询直播小店列表
     */
    @PreAuthorize("@ss.hasPermi('system:store:list')")
    @GetMapping("/list")
    public TableDataInfo list(LiveStore liveStore)
    {
        startPage();
        List<LiveStore> list = liveStoreService.selectLiveStoreList(liveStore);
        return getDataTable(list);
    }

    /**
     * 导出直播小店列表
     */
    @PreAuthorize("@ss.hasPermi('system:store:export')")
    @Log(title = "直播小店", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LiveStore liveStore)
    {
        List<LiveStore> list = liveStoreService.selectLiveStoreList(liveStore);
        ExcelUtil<LiveStore> util = new ExcelUtil<LiveStore>(LiveStore.class);
        util.exportExcel(response, list, "直播小店数据");
    }

    /**
     * 获取直播小店详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:store:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(liveStoreService.selectLiveStoreById(id));
    }

    /**
     * 新增直播小店
     */
    @PreAuthorize("@ss.hasPermi('system:store:add')")
    @Log(title = "直播小店", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LiveStore liveStore)
    {
        return toAjax(liveStoreService.insertLiveStore(liveStore));
    }

    /**
     * 修改直播小店
     */
    @PreAuthorize("@ss.hasPermi('system:store:edit')")
    @Log(title = "直播小店", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LiveStore liveStore)
    {
        return toAjax(liveStoreService.updateLiveStore(liveStore));
    }

    /**
     * 删除直播小店
     */
    @PreAuthorize("@ss.hasPermi('system:store:remove')")
    @Log(title = "直播小店", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(liveStoreService.deleteLiveStoreByIds(ids));
    }



    @GetMapping("/info")
    public AjaxResult liveStoreInfo(Long liveStoreId) {
        return liveStoreService.liveStoreInfo(liveStoreId);
    }
}

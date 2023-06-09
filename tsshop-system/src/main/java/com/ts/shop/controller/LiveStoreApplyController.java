package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ts.shop.domain.LiveStoreApply;
import com.ts.shop.service.ILiveStoreApplyService;
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
 * 直播小店申请Controller
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/system/apply")
public class LiveStoreApplyController extends BaseController
{
    @Autowired
    private ILiveStoreApplyService liveStoreApplyService;

    /**
     * 查询直播小店申请列表
     */
    @PreAuthorize("@ss.hasPermi('system:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(LiveStoreApply liveStoreApply)
    {
        startPage();
        List<LiveStoreApply> list = liveStoreApplyService.selectLiveStoreApplyList(liveStoreApply);
        return getDataTable(list);
    }

    /**
     * 导出直播小店申请列表
     */
    @PreAuthorize("@ss.hasPermi('system:apply:export')")
    @Log(title = "直播小店申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LiveStoreApply liveStoreApply)
    {
        List<LiveStoreApply> list = liveStoreApplyService.selectLiveStoreApplyList(liveStoreApply);
        ExcelUtil<LiveStoreApply> util = new ExcelUtil<LiveStoreApply>(LiveStoreApply.class);
        util.exportExcel(response, list, "直播小店申请数据");
    }

    /**
     * 获取直播小店申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:apply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(liveStoreApplyService.selectLiveStoreApplyById(id));
    }

    /**
     * 新增直播小店申请
     */
    @PreAuthorize("@ss.hasPermi('system:apply:add')")
    @Log(title = "直播小店申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LiveStoreApply liveStoreApply)
    {
        return toAjax(liveStoreApplyService.insertLiveStoreApply(liveStoreApply));
    }

    /**
     * 修改直播小店申请
     */
    @PreAuthorize("@ss.hasPermi('system:apply:edit')")
    @Log(title = "直播小店申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LiveStoreApply liveStoreApply)
    {
        return toAjax(liveStoreApplyService.updateLiveStoreApply(liveStoreApply));
    }

    /**
     * 删除直播小店申请
     */
    @PreAuthorize("@ss.hasPermi('system:apply:remove')")
    @Log(title = "直播小店申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(liveStoreApplyService.deleteLiveStoreApplyByIds(ids));
    }
}

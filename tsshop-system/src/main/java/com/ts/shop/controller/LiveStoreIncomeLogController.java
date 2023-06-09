package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ts.shop.domain.LiveStoreIncomeLog;
import com.ts.shop.service.ILiveStoreIncomeLogService;
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
 * 店铺收入记录Controller
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
@RestController
@RequestMapping("/liveStore/income")
public class LiveStoreIncomeLogController extends BaseController
{
    @Autowired
    private ILiveStoreIncomeLogService liveStoreIncomeLogService;

    /**
     * 查询店铺收入记录列表
     */
    @PreAuthorize("@ss.hasPermi('liveStore:income:list')")
    @GetMapping("/list")
    public TableDataInfo list(LiveStoreIncomeLog liveStoreIncomeLog)
    {
        startPage();
        List<LiveStoreIncomeLog> list = liveStoreIncomeLogService.selectLiveStoreIncomeLogList(liveStoreIncomeLog);
        return getDataTable(list);
    }

    /**
     * 导出店铺收入记录列表
     */
    @PreAuthorize("@ss.hasPermi('liveStore:income:export')")
    @Log(title = "店铺收入记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LiveStoreIncomeLog liveStoreIncomeLog)
    {
        List<LiveStoreIncomeLog> list = liveStoreIncomeLogService.selectLiveStoreIncomeLogList(liveStoreIncomeLog);
        ExcelUtil<LiveStoreIncomeLog> util = new ExcelUtil<LiveStoreIncomeLog>(LiveStoreIncomeLog.class);
        util.exportExcel(response, list, "店铺收入记录数据");
    }

    /**
     * 获取店铺收入记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('liveStore:income:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(liveStoreIncomeLogService.selectLiveStoreIncomeLogById(id));
    }

    /**
     * 新增店铺收入记录
     */
    @PreAuthorize("@ss.hasPermi('liveStore:income:add')")
    @Log(title = "店铺收入记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LiveStoreIncomeLog liveStoreIncomeLog)
    {
        return toAjax(liveStoreIncomeLogService.insertLiveStoreIncomeLog(liveStoreIncomeLog));
    }

    /**
     * 修改店铺收入记录
     */
    @PreAuthorize("@ss.hasPermi('liveStore:income:edit')")
    @Log(title = "店铺收入记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LiveStoreIncomeLog liveStoreIncomeLog)
    {
        return toAjax(liveStoreIncomeLogService.updateLiveStoreIncomeLog(liveStoreIncomeLog));
    }

    /**
     * 删除店铺收入记录
     */
    @PreAuthorize("@ss.hasPermi('liveStore:income:remove')")
    @Log(title = "店铺收入记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(liveStoreIncomeLogService.deleteLiveStoreIncomeLogByIds(ids));
    }
}

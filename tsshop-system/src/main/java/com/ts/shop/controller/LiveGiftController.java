package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ts.shop.domain.LiveGift;
import com.ts.shop.service.ILiveGiftService;
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
 * 直播礼物Controller
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/liveGift/gift")
public class LiveGiftController extends BaseController
{
    @Autowired
    private ILiveGiftService liveGiftService;

    /**
     * 查询直播礼物列表
     */
    @PreAuthorize("@ss.hasPermi('liveGift:gift:list')")
    @GetMapping("/list")
    public TableDataInfo list(LiveGift liveGift)
    {
        startPage();
        List<LiveGift> list = liveGiftService.selectLiveGiftList(liveGift);
        return getDataTable(list);
    }

    /**
     * 导出直播礼物列表
     */
    @PreAuthorize("@ss.hasPermi('liveGift:gift:export')")
    @Log(title = "直播礼物", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LiveGift liveGift)
    {
        List<LiveGift> list = liveGiftService.selectLiveGiftList(liveGift);
        ExcelUtil<LiveGift> util = new ExcelUtil<LiveGift>(LiveGift.class);
        util.exportExcel(response, list, "直播礼物数据");
    }

    /**
     * 获取直播礼物详细信息
     */
    @PreAuthorize("@ss.hasPermi('liveGift:gift:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(liveGiftService.selectLiveGiftById(id));
    }

    /**
     * 新增直播礼物
     */
    @PreAuthorize("@ss.hasPermi('liveGift:gift:add')")
    @Log(title = "直播礼物", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LiveGift liveGift)
    {
        if (liveGift.getUnitPrice().scale() > 2){
            return AjaxResult.error("金额只允许输入两位小数");
        }
        return toAjax(liveGiftService.insertLiveGift(liveGift));
    }

    /**
     * 修改直播礼物
     */
    @PreAuthorize("@ss.hasPermi('liveGift:gift:edit')")
    @Log(title = "直播礼物", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LiveGift liveGift)
    {
        if (liveGift.getUnitPrice().scale() > 2){
            return AjaxResult.error("金额只允许输入两位小数");
        }
        return toAjax(liveGiftService.updateLiveGift(liveGift));
    }

    /**
     * 删除直播礼物
     */
    @PreAuthorize("@ss.hasPermi('liveGift:gift:remove')")
    @Log(title = "直播礼物", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(liveGiftService.deleteLiveGiftByIds(ids));
    }
}

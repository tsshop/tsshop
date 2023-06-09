package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ts.shop.domain.LiveGiftDetail;
import com.ts.shop.service.ILiveGiftDetailService;
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
 * 直播礼物赠送记录Controller
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
@RestController
@RequestMapping("/liveGift/giftDetail")
public class LiveGiftDetailController extends BaseController
{
    @Autowired
    private ILiveGiftDetailService liveGiftDetailService;

    /**
     * 查询直播礼物赠送记录列表
     */
    @PreAuthorize("@ss.hasPermi('liveGift:giftDetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(LiveGiftDetail liveGiftDetail)
    {
        startPage();
        List<LiveGiftDetail> list = liveGiftDetailService.selectLiveGiftDetailList(liveGiftDetail);
        return getDataTable(list);
    }

    /**
     * 导出直播礼物赠送记录列表
     */
    @PreAuthorize("@ss.hasPermi('liveGift:giftDetail:export')")
    @Log(title = "直播礼物赠送记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LiveGiftDetail liveGiftDetail)
    {
        List<LiveGiftDetail> list = liveGiftDetailService.selectLiveGiftDetailList(liveGiftDetail);
        ExcelUtil<LiveGiftDetail> util = new ExcelUtil<LiveGiftDetail>(LiveGiftDetail.class);
        util.exportExcel(response, list, "直播礼物赠送记录数据");
    }

    /**
     * 获取直播礼物赠送记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('liveGift:giftDetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(liveGiftDetailService.selectLiveGiftDetailById(id));
    }

    /**
     * 新增直播礼物赠送记录
     */
    @PreAuthorize("@ss.hasPermi('liveGift:giftDetail:add')")
    @Log(title = "直播礼物赠送记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LiveGiftDetail liveGiftDetail)
    {
        return toAjax(liveGiftDetailService.insertLiveGiftDetail(liveGiftDetail));
    }

    /**
     * 修改直播礼物赠送记录
     */
    @PreAuthorize("@ss.hasPermi('liveGift:giftDetail:edit')")
    @Log(title = "直播礼物赠送记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LiveGiftDetail liveGiftDetail)
    {
        return toAjax(liveGiftDetailService.updateLiveGiftDetail(liveGiftDetail));
    }

    /**
     * 删除直播礼物赠送记录
     */
    @PreAuthorize("@ss.hasPermi('liveGift:giftDetail:remove')")
    @Log(title = "直播礼物赠送记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(liveGiftDetailService.deleteLiveGiftDetailByIds(ids));
    }
}

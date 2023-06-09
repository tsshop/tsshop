package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ts.shop.domain.LiveBroadcast;
import com.ts.shop.service.ILiveBroadcastService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 直播记录Controller
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
@RestController
@RequestMapping("/shop/broadcast")
public class LiveBroadcastController extends BaseController
{
    @Autowired
    private ILiveBroadcastService liveBroadcastService;

    /**
     * 查询直播记录列表
     */
    @PreAuthorize("@ss.hasPermi('shop:broadcast:list')")
    @GetMapping("/list")
    public TableDataInfo list(LiveBroadcast liveBroadcast)
    {
        startPage();
        List<LiveBroadcast> list = liveBroadcastService.selectLiveBroadcastList(liveBroadcast);
        return getDataTable(list);
    }

    /**
     * 导出直播记录列表
     */
    @PreAuthorize("@ss.hasPermi('shop:broadcast:export')")
    @Log(title = "直播记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LiveBroadcast liveBroadcast)
    {
        List<LiveBroadcast> list = liveBroadcastService.selectLiveBroadcastList(liveBroadcast);
        ExcelUtil<LiveBroadcast> util = new ExcelUtil<LiveBroadcast>(LiveBroadcast.class);
        util.exportExcel(response, list, "直播记录数据");
    }

    /**
     * 获取直播记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:broadcast:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(liveBroadcastService.selectLiveBroadcastById(id));
    }

    /**
     * 新增直播记录
     */
    @PreAuthorize("@ss.hasPermi('shop:broadcast:add')")
    @Log(title = "直播记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LiveBroadcast liveBroadcast)
    {
        return toAjax(liveBroadcastService.insertLiveBroadcast(liveBroadcast));
    }

    /**
     * 修改直播记录
     */
    @PreAuthorize("@ss.hasPermi('shop:broadcast:edit')")
    @Log(title = "直播记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LiveBroadcast liveBroadcast)
    {
        return toAjax(liveBroadcastService.updateLiveBroadcast(liveBroadcast));
    }

    /**
     * 删除直播记录
     */
    @PreAuthorize("@ss.hasPermi('shop:broadcast:remove')")
    @Log(title = "直播记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(liveBroadcastService.deleteLiveBroadcastByIds(ids));
    }
}

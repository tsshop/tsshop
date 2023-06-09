package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ts.shop.domain.LiveStoreWithdrawalsLog;
import com.ts.shop.domain.pay.PayEnums;
import com.ts.shop.service.ILiveStoreWithdrawalsLogService;
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
 * 店铺提现记录Controller
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/system/log")
public class LiveStoreWithdrawalsLogController extends BaseController
{
    @Autowired
    private ILiveStoreWithdrawalsLogService liveStoreWithdrawalsLogService;

    /**
     * 查询店铺提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(LiveStoreWithdrawalsLog liveStoreWithdrawalsLog)
    {
        startPage();
        List<LiveStoreWithdrawalsLog> list = liveStoreWithdrawalsLogService.selectLiveStoreWithdrawalsLogList(liveStoreWithdrawalsLog);
        list.forEach(item ->{
            if (item.getWithdrawalsType().equals(PayEnums.ProviderEnum.ALI_PAY.getCode())
                    || item.getWithdrawalsType().equals(PayEnums.PayTypeEnum.ALI_PAY_APP.getCode())){
                item.setWithdrawalsType("支付宝");
            }
        });
        return getDataTable(list);
    }

    /**
     * 导出店铺提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:export')")
    @Log(title = "店铺提现记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LiveStoreWithdrawalsLog liveStoreWithdrawalsLog)
    {
        List<LiveStoreWithdrawalsLog> list = liveStoreWithdrawalsLogService.selectLiveStoreWithdrawalsLogList(liveStoreWithdrawalsLog);
        ExcelUtil<LiveStoreWithdrawalsLog> util = new ExcelUtil<LiveStoreWithdrawalsLog>(LiveStoreWithdrawalsLog.class);
        util.exportExcel(response, list, "店铺提现记录数据");
    }

    /**
     * 获取店铺提现记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(liveStoreWithdrawalsLogService.selectLiveStoreWithdrawalsLogById(id));
    }

    /**
     * 新增店铺提现记录
     */
    @PreAuthorize("@ss.hasPermi('system:log:add')")
    @Log(title = "店铺提现记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LiveStoreWithdrawalsLog liveStoreWithdrawalsLog)
    {
        return toAjax(liveStoreWithdrawalsLogService.insertLiveStoreWithdrawalsLog(liveStoreWithdrawalsLog));
    }

    /**
     * 修改店铺提现记录
     */
    @PreAuthorize("@ss.hasPermi('system:log:edit')")
    @Log(title = "店铺提现记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LiveStoreWithdrawalsLog liveStoreWithdrawalsLog)
    {
        return toAjax(liveStoreWithdrawalsLogService.updateLiveStoreWithdrawalsLog(liveStoreWithdrawalsLog));
    }

    /**
     * 删除店铺提现记录
     */
    @PreAuthorize("@ss.hasPermi('system:log:remove')")
    @Log(title = "店铺提现记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(liveStoreWithdrawalsLogService.deleteLiveStoreWithdrawalsLogByIds(ids));
    }
}

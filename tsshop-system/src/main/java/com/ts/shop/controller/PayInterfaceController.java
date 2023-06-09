package com.ts.shop.controller;

import com.ts.common.annotation.Log;
import com.ts.common.core.controller.BaseController;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.core.page.TableDataInfo;
import com.ts.common.enums.BusinessType;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.shop.domain.PayInterface;
import com.ts.shop.domain.pay.PayEnums;
import com.ts.shop.service.IPayInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付接口Controller
 *
 * @author xu
 * @date 2023-04-25
 */
@RestController
@RequestMapping("/pay/interface")
public class PayInterfaceController extends BaseController
{
    @Autowired
    private IPayInterfaceService payInterfaceService;

    @GetMapping("/getType")
    public AjaxResult getType () {
        Map<Object, Object> result = new HashMap<>();
        Arrays.stream(PayEnums.ProviderEnum.values()).forEach(item -> {
            result.put(item.getCode(), item.getMsg());
        });
        return AjaxResult.success(result);
    }

    /**
     * 查询支付接口列表
     */
    @PreAuthorize("@ss.hasPermi('pay:interface:list')")
    @GetMapping("/list")
    public TableDataInfo list(PayInterface payInterface)
    {
        startPage();
        List<PayInterface> list = payInterfaceService.selectPayInterfaceList(payInterface);
        return getDataTable(list);
    }

    /**
     * 导出支付接口列表
     */
    @PreAuthorize("@ss.hasPermi('pay:interface:export')")
    @Log(title = "支付接口", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayInterface payInterface)
    {
        List<PayInterface> list = payInterfaceService.selectPayInterfaceList(payInterface);
        ExcelUtil<PayInterface> util = new ExcelUtil<PayInterface>(PayInterface.class);
        util.exportExcel(response, list, "支付接口数据");
    }

    /**
     * 获取支付接口详细信息
     */
    @PreAuthorize("@ss.hasPermi('pay:interface:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(payInterfaceService.selectPayInterfaceById(id));
    }

    /**
     * 新增支付接口
     */
    @PreAuthorize("@ss.hasPermi('pay:interface:add')")
    @Log(title = "支付接口", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayInterface payInterface)
    {
        return toAjax(payInterfaceService.insertPayInterface(payInterface));
    }

    /**
     * 修改支付接口
     */
    @PreAuthorize("@ss.hasPermi('pay:interface:edit')")
    @Log(title = "支付接口", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayInterface payInterface)
    {
        Integer count = payInterfaceService.check(payInterface.getId());
        if (count > 0){
            return AjaxResult.error("已经被使用的支付接口无法修改或删除");
        }
        return toAjax(payInterfaceService.updatePayInterface(payInterface));
    }

    /**
     * 接口开关
     */
    @PreAuthorize("@ss.hasPermi('pay:interface:close')")
    @Log(title = "支付通道", businessType = BusinessType.UPDATE)
    @PutMapping("/close")
    public AjaxResult close(@RequestBody PayInterface payInterface)
    {
        PayInterface oldPayInterface = payInterfaceService.selectPayInterfaceById(payInterface.getId());
        oldPayInterface.setIsUse(payInterface.getIsUse());
        payInterfaceService.updatePayInterface(oldPayInterface);
        return toAjax(true);
    }

    /**
     * 删除支付接口
     */
    @PreAuthorize("@ss.hasPermi('pay:interface:remove')")
    @Log(title = "支付接口", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        Integer count = payInterfaceService.check(id);
        if (count > 0){
            return AjaxResult.error("已经被使用的支付接口无法修改或删除");
        }
        return toAjax(payInterfaceService.deletePayInterfaceById(id));
    }
}

package com.ts.shop.controller;


import com.ts.common.annotation.Log;
import com.ts.common.core.controller.BaseController;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.core.page.TableDataInfo;
import com.ts.common.enums.BusinessType;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.shop.domain.PayInterface;
import com.ts.shop.domain.PayThoroughfare;
import com.ts.shop.domain.pay.PayEnums;
import com.ts.shop.service.IPayInterfaceService;
import com.ts.shop.service.IPayThoroughfareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 支付通道Controller
 *
 * @author xu
 * @date 2023-04-25
 */
@RestController
@RequestMapping("/pay/thoroughfare")
public class PayThoroughfareController extends BaseController
{
    @Autowired
    private IPayThoroughfareService payThoroughfareService;

    @Autowired
    private IPayInterfaceService payInterfaceService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询支付通道列表
     */
    @PreAuthorize("@ss.hasPermi('pay:thoroughfare:list')")
    @GetMapping("/list")
    public TableDataInfo list(PayThoroughfare payThoroughfare)
    {
        startPage();
        List<PayThoroughfare> list = payThoroughfareService.selectPayThoroughfareList(payThoroughfare);
        return getDataTable(list);
    }

    /**
     * 导出支付通道列表
     */
    @PreAuthorize("@ss.hasPermi('pay:thoroughfare:export')")
    @Log(title = "支付通道", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayThoroughfare payThoroughfare)
    {
        List<PayThoroughfare> list = payThoroughfareService.selectPayThoroughfareList(payThoroughfare);
        ExcelUtil<PayThoroughfare> util = new ExcelUtil<PayThoroughfare>(PayThoroughfare.class);
        util.exportExcel(response, list, "支付通道数据");
    }

    /**
     * 获取支付通道详细信息
     */
    @PreAuthorize("@ss.hasPermi('pay:thoroughfare:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        PayThoroughfare payThoroughfare = payThoroughfareService.selectPayThoroughfareById(id);
        PayInterface payInterface = payInterfaceService.selectPayInterfaceById(payThoroughfare.getInterfaceId());
        payThoroughfare.setPayInterface(payInterface);
        return AjaxResult.success(payThoroughfare);
    }

    /**
     * 新增支付通道
     */
    @PreAuthorize("@ss.hasPermi('pay:thoroughfare:add')")
    @Log(title = "支付通道", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayThoroughfare payThoroughfare)
    {
        payThoroughfareService.insertPayThoroughfare(payThoroughfare);
        redisTemplate.opsForValue().set("pay:updateTime",new Date().getTime());
        return toAjax(true);
    }

    /**
     * 修改支付通道
     */
    @PreAuthorize("@ss.hasPermi('pay:thoroughfare:edit')")
    @Log(title = "支付通道", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayThoroughfare payThoroughfare)
    {
        PayThoroughfare old = payThoroughfareService.selectPayThoroughfareById(payThoroughfare.getId());
        if (old.getIsUse()){
            return AjaxResult.error("请先关闭支付通道");
        }
        payThoroughfare.setIsUse(false);
        payThoroughfareService.updatePayThoroughfare(payThoroughfare);
        return toAjax(true);
    }

    /**
     * 通道开关
     */
    @PreAuthorize("@ss.hasPermi('pay:thoroughfare:close')")
    @Log(title = "支付通道", businessType = BusinessType.UPDATE)
    @PutMapping("/close")
    public AjaxResult close(@RequestBody PayThoroughfare payThoroughfare)
    {
        PayThoroughfare oldPayThoroughfare = payThoroughfareService.selectPayThoroughfareById(payThoroughfare.getId());
        oldPayThoroughfare.setIsUse(payThoroughfare.getIsUse());
        payThoroughfareService.updatePayThoroughfare(oldPayThoroughfare);
        redisTemplate.opsForValue().set("pay:updateTime",new Date().getTime());
        return toAjax(true);
    }

    /**
     * 通道开关
     */
    @PreAuthorize("@ss.hasPermi('pay:thoroughfare:close')")
    @Log(title = "支付通道", businessType = BusinessType.UPDATE)
    @PutMapping("/supportWithdrawals/close")
    public AjaxResult closeSupportWithdrawals(@RequestBody PayThoroughfare payThoroughfare)
    {
        PayThoroughfare oldPayThoroughfare = payThoroughfareService.selectPayThoroughfareById(payThoroughfare.getId());
        if (!Objects.equals(oldPayThoroughfare.getPayProvider(), PayEnums.ProviderEnum.ALI_PAY.getCode())){
            return AjaxResult.error("暂时只支持支付宝提现");
        }
        oldPayThoroughfare.setSupportWithdrawals(payThoroughfare.getSupportWithdrawals());
        payThoroughfareService.updatePayThoroughfare(oldPayThoroughfare);
        redisTemplate.opsForValue().set("pay:updateTime",new Date().getTime());
        return toAjax(true);
    }

    /**
     * 删除支付通道
     */
    @PreAuthorize("@ss.hasPermi('pay:thoroughfare:remove')")
    @Log(title = "支付通道", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        PayThoroughfare old = payThoroughfareService.selectPayThoroughfareById(id);
        if (old.getIsUse()){
            return AjaxResult.error("请先关闭支付通道");
        }
        return toAjax(payThoroughfareService.deletePayThoroughfareById(id));
    }
}

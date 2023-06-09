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
import com.ts.shop.domain.Delivery;
import com.ts.shop.service.IDeliveryService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 物流公司Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/delivery")
public class DeliveryController extends BaseController
{
    @Autowired
    private IDeliveryService deliveryService;

    /**
     * 查询物流公司列表
     */
    @PreAuthorize("@ss.hasPermi('shop:delivery:list')")
    @GetMapping("/list")
    public TableDataInfo list(Delivery delivery)
    {
        startPage();
        List<Delivery> list = deliveryService.selectDeliveryList(delivery);
        return getDataTable(list);
    }

    /**
     * 导出物流公司列表
     */
    @PreAuthorize("@ss.hasPermi('shop:delivery:export')")
    @Log(title = "物流公司", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Delivery delivery)
    {
        List<Delivery> list = deliveryService.selectDeliveryList(delivery);
        ExcelUtil<Delivery> util = new ExcelUtil<Delivery>(Delivery.class);
        util.exportExcel(response, list, "物流公司数据");
    }

    /**
     * 获取物流公司详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:delivery:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(deliveryService.selectDeliveryById(id));
    }

    /**
     * 新增物流公司
     */
    @PreAuthorize("@ss.hasPermi('shop:delivery:add')")
    @Log(title = "物流公司", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Delivery delivery)
    {
        return toAjax(deliveryService.insertDelivery(delivery));
    }

    /**
     * 修改物流公司
     */
    @PreAuthorize("@ss.hasPermi('shop:delivery:edit')")
    @Log(title = "物流公司", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Delivery delivery)
    {
        return toAjax(deliveryService.updateDelivery(delivery));
    }

    /**
     * 删除物流公司
     */
    @PreAuthorize("@ss.hasPermi('shop:delivery:remove')")
    @Log(title = "物流公司", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(deliveryService.deleteDeliveryByIds(ids));
    }
}

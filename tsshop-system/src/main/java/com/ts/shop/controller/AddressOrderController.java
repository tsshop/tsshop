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
import com.ts.shop.domain.AddressOrder;
import com.ts.shop.service.IAddressOrderService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 用户收货地址Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/addressOrder")
public class AddressOrderController extends BaseController
{
    @Autowired
    private IAddressOrderService addressOrderService;

    /**
     * 查询用户收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('shop:addressOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(AddressOrder addressOrder)
    {
        startPage();
        List<AddressOrder> list = addressOrderService.selectAddressOrderList(addressOrder);
        return getDataTable(list);
    }

    /**
     * 导出用户收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('shop:addressOrder:export')")
    @Log(title = "用户收货地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AddressOrder addressOrder)
    {
        List<AddressOrder> list = addressOrderService.selectAddressOrderList(addressOrder);
        ExcelUtil<AddressOrder> util = new ExcelUtil<AddressOrder>(AddressOrder.class);
        util.exportExcel(response, list, "用户收货地址数据");
    }

    /**
     * 获取用户收货地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:addressOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(addressOrderService.selectAddressOrderById(id));
    }

    /**
     * 新增用户收货地址
     */
    @PreAuthorize("@ss.hasPermi('shop:addressOrder:add')")
    @Log(title = "用户收货地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AddressOrder addressOrder)
    {
        return toAjax(addressOrderService.insertAddressOrder(addressOrder));
    }

    /**
     * 修改用户收货地址
     */
    @PreAuthorize("@ss.hasPermi('shop:addressOrder:edit')")
    @Log(title = "用户收货地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AddressOrder addressOrder)
    {
        return toAjax(addressOrderService.updateAddressOrder(addressOrder));
    }

    /**
     * 删除用户收货地址
     */
    @PreAuthorize("@ss.hasPermi('shop:addressOrder:remove')")
    @Log(title = "用户收货地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(addressOrderService.deleteAddressOrderByIds(ids));
    }
}

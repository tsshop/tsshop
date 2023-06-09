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
import com.ts.shop.domain.Address;
import com.ts.shop.service.IAddressService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 用户收货地址Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/address")
public class AddressController extends BaseController
{
    @Autowired
    private IAddressService addressService;

    /**
     * 查询用户收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('shop:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(Address address)
    {
        startPage();
        List<Address> list = addressService.selectAddressList(address);
        return getDataTable(list);
    }

    /**
     * 导出用户收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('shop:address:export')")
    @Log(title = "用户收货地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Address address)
    {
        List<Address> list = addressService.selectAddressList(address);
        ExcelUtil<Address> util = new ExcelUtil<Address>(Address.class);
        util.exportExcel(response, list, "用户收货地址数据");
    }

    /**
     * 获取用户收货地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:address:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(addressService.selectAddressById(id));
    }

    /**
     * 新增用户收货地址
     */
    @PreAuthorize("@ss.hasPermi('shop:address:add')")
    @Log(title = "用户收货地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Address address)
    {
        return toAjax(addressService.insertAddress(address));
    }

    /**
     * 修改用户收货地址
     */
    @PreAuthorize("@ss.hasPermi('shop:address:edit')")
    @Log(title = "用户收货地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Address address)
    {
        return toAjax(addressService.updateAddress(address));
    }

    /**
     * 删除用户收货地址
     */
    @PreAuthorize("@ss.hasPermi('shop:address:remove')")
    @Log(title = "用户收货地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(addressService.deleteAddressByIds(ids));
    }
}

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
import com.ts.shop.domain.ShopCart;
import com.ts.shop.service.IShopCartService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 购物车Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/shopCart")
public class ShopCartController extends BaseController
{
    @Autowired
    private IShopCartService shopCartService;

    /**
     * 查询购物车列表
     */
    @PreAuthorize("@ss.hasPermi('shop:shopCart:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopCart shopCart)
    {
        startPage();
        List<ShopCart> list = shopCartService.selectShopCartList(shopCart);
        return getDataTable(list);
    }

    /**
     * 导出购物车列表
     */
    @PreAuthorize("@ss.hasPermi('shop:shopCart:export')")
    @Log(title = "购物车", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopCart shopCart)
    {
        List<ShopCart> list = shopCartService.selectShopCartList(shopCart);
        ExcelUtil<ShopCart> util = new ExcelUtil<ShopCart>(ShopCart.class);
        util.exportExcel(response, list, "购物车数据");
    }

    /**
     * 获取购物车详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:shopCart:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shopCartService.selectShopCartById(id));
    }

    /**
     * 新增购物车
     */
    @PreAuthorize("@ss.hasPermi('shop:shopCart:add')")
    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopCart shopCart)
    {
        return toAjax(shopCartService.insertShopCart(shopCart));
    }

    /**
     * 修改购物车
     */
    @PreAuthorize("@ss.hasPermi('shop:shopCart:edit')")
    @Log(title = "购物车", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopCart shopCart)
    {
        return toAjax(shopCartService.updateShopCart(shopCart));
    }

    /**
     * 删除购物车
     */
    @PreAuthorize("@ss.hasPermi('shop:shopCart:remove')")
    @Log(title = "购物车", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shopCartService.deleteShopCartByIds(ids));
    }
}

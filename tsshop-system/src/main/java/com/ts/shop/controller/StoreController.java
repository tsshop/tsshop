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
import com.ts.shop.domain.Store;
import com.ts.shop.service.IStoreService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 店铺Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/store")
public class StoreController extends BaseController
{
    @Autowired
    private IStoreService storeService;

    /**
     * 查询店铺列表
     */
    @PreAuthorize("@ss.hasPermi('shop:store:list')")
    @GetMapping("/list")
    public TableDataInfo list(Store store)
    {
        startPage();
        List<Store> list = storeService.selectStoreList(store);
        return getDataTable(list);
    }

    /**
     * 导出店铺列表
     */
    @PreAuthorize("@ss.hasPermi('shop:store:export')")
    @Log(title = "店铺", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Store store)
    {
        List<Store> list = storeService.selectStoreList(store);
        ExcelUtil<Store> util = new ExcelUtil<Store>(Store.class);
        util.exportExcel(response, list, "店铺数据");
    }

    /**
     * 获取店铺详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:store:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(storeService.selectStoreById(id));
    }

    /**
     * 新增店铺
     */
    @PreAuthorize("@ss.hasPermi('shop:store:add')")
    @Log(title = "店铺", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Store store)
    {
        return toAjax(storeService.insertStore(store));
    }

    /**
     * 修改店铺
     */
    @PreAuthorize("@ss.hasPermi('shop:store:edit')")
    @Log(title = "店铺", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Store store)
    {
        return toAjax(storeService.updateStore(store));
    }

    /**
     * 删除店铺
     */
    @PreAuthorize("@ss.hasPermi('shop:store:remove')")
    @Log(title = "店铺", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(storeService.deleteStoreByIds(ids));
    }
}

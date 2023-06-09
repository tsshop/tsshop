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
import com.ts.shop.domain.StoreCollect;
import com.ts.shop.service.IStoreCollectService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 店铺收藏Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/storeCollect")
public class StoreCollectController extends BaseController
{
    @Autowired
    private IStoreCollectService storeCollectService;

    /**
     * 查询店铺收藏列表
     */
    @PreAuthorize("@ss.hasPermi('shop:storeCollect:list')")
    @GetMapping("/list")
    public TableDataInfo list(StoreCollect storeCollect)
    {
        startPage();
        List<StoreCollect> list = storeCollectService.selectStoreCollectList(storeCollect);
        return getDataTable(list);
    }

    /**
     * 导出店铺收藏列表
     */
    @PreAuthorize("@ss.hasPermi('shop:storeCollect:export')")
    @Log(title = "店铺收藏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreCollect storeCollect)
    {
        List<StoreCollect> list = storeCollectService.selectStoreCollectList(storeCollect);
        ExcelUtil<StoreCollect> util = new ExcelUtil<StoreCollect>(StoreCollect.class);
        util.exportExcel(response, list, "店铺收藏数据");
    }

    /**
     * 获取店铺收藏详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:storeCollect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(storeCollectService.selectStoreCollectById(id));
    }

    /**
     * 新增店铺收藏
     */
    @PreAuthorize("@ss.hasPermi('shop:storeCollect:add')")
    @Log(title = "店铺收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreCollect storeCollect)
    {
        return toAjax(storeCollectService.insertStoreCollect(storeCollect));
    }

    /**
     * 修改店铺收藏
     */
    @PreAuthorize("@ss.hasPermi('shop:storeCollect:edit')")
    @Log(title = "店铺收藏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreCollect storeCollect)
    {
        return toAjax(storeCollectService.updateStoreCollect(storeCollect));
    }

    /**
     * 删除店铺收藏
     */
    @PreAuthorize("@ss.hasPermi('shop:storeCollect:remove')")
    @Log(title = "店铺收藏", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(storeCollectService.deleteStoreCollectByIds(ids));
    }
}

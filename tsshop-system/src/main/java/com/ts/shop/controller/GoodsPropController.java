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
import com.ts.shop.domain.GoodsProp;
import com.ts.shop.service.IGoodsPropService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 规格Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/goodsProp")
public class GoodsPropController extends BaseController
{
    @Autowired
    private IGoodsPropService goodsPropService;

    /**
     * 查询规格列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsProp:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsProp goodsProp)
    {
        startPage();
        List<GoodsProp> list = goodsPropService.selectGoodsPropList(goodsProp);
        return getDataTable(list);
    }

    /**
     * 导出规格列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsProp:export')")
    @Log(title = "规格", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsProp goodsProp)
    {
        List<GoodsProp> list = goodsPropService.selectGoodsPropList(goodsProp);
        ExcelUtil<GoodsProp> util = new ExcelUtil<GoodsProp>(GoodsProp.class);
        util.exportExcel(response, list, "规格数据");
    }

    /**
     * 获取规格详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsProp:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(goodsPropService.selectGoodsPropById(id));
    }

    /**
     * 新增规格
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsProp:add')")
    @Log(title = "规格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsProp goodsProp)
    {
        return toAjax(goodsPropService.insertGoodsProp(goodsProp));
    }

    /**
     * 修改规格
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsProp:edit')")
    @Log(title = "规格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsProp goodsProp)
    {
        return toAjax(goodsPropService.updateGoodsProp(goodsProp));
    }

    /**
     * 删除规格
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsProp:remove')")
    @Log(title = "规格", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(goodsPropService.deleteGoodsPropByIds(ids));
    }
}

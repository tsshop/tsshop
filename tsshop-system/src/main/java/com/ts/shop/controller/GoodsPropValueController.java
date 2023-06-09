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
import com.ts.shop.domain.GoodsPropValue;
import com.ts.shop.service.IGoodsPropValueService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 规格参数Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/goodsPropValue")
public class GoodsPropValueController extends BaseController
{
    @Autowired
    private IGoodsPropValueService goodsPropValueService;

    /**
     * 查询规格参数列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsPropValue:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsPropValue goodsPropValue)
    {
        startPage();
        List<GoodsPropValue> list = goodsPropValueService.selectGoodsPropValueList(goodsPropValue);
        return getDataTable(list);
    }

    /**
     * 导出规格参数列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsPropValue:export')")
    @Log(title = "规格参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsPropValue goodsPropValue)
    {
        List<GoodsPropValue> list = goodsPropValueService.selectGoodsPropValueList(goodsPropValue);
        ExcelUtil<GoodsPropValue> util = new ExcelUtil<GoodsPropValue>(GoodsPropValue.class);
        util.exportExcel(response, list, "规格参数数据");
    }

    /**
     * 获取规格参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsPropValue:query')")
    @GetMapping(value = "/{valueId}")
    public AjaxResult getInfo(@PathVariable("valueId") Long valueId)
    {
        return success(goodsPropValueService.selectGoodsPropValueByValueId(valueId));
    }

    /**
     * 新增规格参数
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsPropValue:add')")
    @Log(title = "规格参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsPropValue goodsPropValue)
    {
        return toAjax(goodsPropValueService.insertGoodsPropValue(goodsPropValue));
    }

    /**
     * 修改规格参数
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsPropValue:edit')")
    @Log(title = "规格参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsPropValue goodsPropValue)
    {
        return toAjax(goodsPropValueService.updateGoodsPropValue(goodsPropValue));
    }

    /**
     * 删除规格参数
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsPropValue:remove')")
    @Log(title = "规格参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{valueIds}")
    public AjaxResult remove(@PathVariable Long[] valueIds)
    {
        return toAjax(goodsPropValueService.deleteGoodsPropValueByValueIds(valueIds));
    }
}

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
import com.ts.shop.domain.GoodsSku;
import com.ts.shop.service.IGoodsSkuService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 单品SKUController
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/goodsSku")
public class GoodsSkuController extends BaseController
{
    @Autowired
    private IGoodsSkuService goodsSkuService;

    /**
     * 查询单品SKU列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsSku:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsSku goodsSku)
    {
        startPage();
        List<GoodsSku> list = goodsSkuService.selectGoodsSkuList(goodsSku);
        return getDataTable(list);
    }

    /**
     * 导出单品SKU列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsSku:export')")
    @Log(title = "单品SKU", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsSku goodsSku)
    {
        List<GoodsSku> list = goodsSkuService.selectGoodsSkuList(goodsSku);
        ExcelUtil<GoodsSku> util = new ExcelUtil<GoodsSku>(GoodsSku.class);
        util.exportExcel(response, list, "单品SKU数据");
    }

    /**
     * 获取单品SKU详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsSku:query')")
    @GetMapping(value = "/{skuId}")
    public AjaxResult getInfo(@PathVariable("skuId") Long skuId)
    {
        return success(goodsSkuService.selectGoodsSkuBySkuId(skuId));
    }

    /**
     * 新增单品SKU
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsSku:add')")
    @Log(title = "单品SKU", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsSku goodsSku)
    {
        return toAjax(goodsSkuService.insertGoodsSku(goodsSku));
    }

    /**
     * 修改单品SKU
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsSku:edit')")
    @Log(title = "单品SKU", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsSku goodsSku)
    {
        return toAjax(goodsSkuService.updateGoodsSku(goodsSku));
    }

    /**
     * 删除单品SKU
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsSku:remove')")
    @Log(title = "单品SKU", businessType = BusinessType.DELETE)
	@DeleteMapping("/{skuIds}")
    public AjaxResult remove(@PathVariable Long[] skuIds)
    {
        return toAjax(goodsSkuService.deleteGoodsSkuBySkuIds(skuIds));
    }
}

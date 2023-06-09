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
import com.ts.shop.domain.GoodsType;
import com.ts.shop.service.IGoodsTypeService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 商品类型Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/goodsType")
public class GoodsTypeController extends BaseController
{
    @Autowired
    private IGoodsTypeService goodsTypeService;

    /**
     * 查询商品类型列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsType:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsType goodsType)
    {
        startPage();
        List<GoodsType> list = goodsTypeService.selectGoodsTypeList(goodsType);
        return getDataTable(list);
    }

    /**
     * 导出商品类型列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsType:export')")
    @Log(title = "商品类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsType goodsType)
    {
        List<GoodsType> list = goodsTypeService.selectGoodsTypeList(goodsType);
        ExcelUtil<GoodsType> util = new ExcelUtil<GoodsType>(GoodsType.class);
        util.exportExcel(response, list, "商品类型数据");
    }

    /**
     * 获取商品类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsType:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(goodsTypeService.selectGoodsTypeById(id));
    }

    /**
     * 新增商品类型
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsType:add')")
    @Log(title = "商品类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsType goodsType)
    {
        return toAjax(goodsTypeService.insertGoodsType(goodsType));
    }

    /**
     * 修改商品类型
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsType:edit')")
    @Log(title = "商品类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsType goodsType)
    {
        return toAjax(goodsTypeService.updateGoodsType(goodsType));
    }

    /**
     * 删除商品类型
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsType:remove')")
    @Log(title = "商品类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(goodsTypeService.deleteGoodsTypeByIds(ids));
    }
}

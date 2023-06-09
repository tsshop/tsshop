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
import com.ts.shop.domain.GoodsCollect;
import com.ts.shop.service.IGoodsCollectService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 商品收藏Controller
 * 
 * @author xu
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/goodsCollect")
public class GoodsCollectController extends BaseController
{
    @Autowired
    private IGoodsCollectService goodsCollectService;

    /**
     * 查询商品收藏列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsCollect:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsCollect goodsCollect)
    {
        startPage();
        List<GoodsCollect> list = goodsCollectService.selectGoodsCollectList(goodsCollect);
        return getDataTable(list);
    }

    /**
     * 导出商品收藏列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsCollect:export')")
    @Log(title = "商品收藏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsCollect goodsCollect)
    {
        List<GoodsCollect> list = goodsCollectService.selectGoodsCollectList(goodsCollect);
        ExcelUtil<GoodsCollect> util = new ExcelUtil<GoodsCollect>(GoodsCollect.class);
        util.exportExcel(response, list, "商品收藏数据");
    }

    /**
     * 获取商品收藏详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsCollect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(goodsCollectService.selectGoodsCollectById(id));
    }

    /**
     * 新增商品收藏
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsCollect:add')")
    @Log(title = "商品收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsCollect goodsCollect)
    {
        return toAjax(goodsCollectService.insertGoodsCollect(goodsCollect));
    }

    /**
     * 修改商品收藏
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsCollect:edit')")
    @Log(title = "商品收藏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsCollect goodsCollect)
    {
        return toAjax(goodsCollectService.updateGoodsCollect(goodsCollect));
    }

    /**
     * 删除商品收藏
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsCollect:remove')")
    @Log(title = "商品收藏", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(goodsCollectService.deleteGoodsCollectByIds(ids));
    }
}

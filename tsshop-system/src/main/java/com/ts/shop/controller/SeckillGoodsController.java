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
import com.ts.shop.domain.SeckillGoods;
import com.ts.shop.service.ISeckillGoodsService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 秒杀活动商品Controller
 * 
 * @author xu
 * @date 2023-02-21
 */
@RestController
@RequestMapping("/shop/seckillGoods")
public class SeckillGoodsController extends BaseController
{
    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    /**
     * 查询秒杀活动商品列表
     */
    @PreAuthorize("@ss.hasPermi('shop:seckillGoods:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeckillGoods seckillGoods)
    {
        startPage();
        List<SeckillGoods> list = seckillGoodsService.selectSeckillGoodsList(seckillGoods);
        return getDataTable(list);
    }

    /**
     * 导出秒杀活动商品列表
     */
    @PreAuthorize("@ss.hasPermi('shop:seckillGoods:export')")
    @Log(title = "秒杀活动商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeckillGoods seckillGoods)
    {
        List<SeckillGoods> list = seckillGoodsService.selectSeckillGoodsList(seckillGoods);
        ExcelUtil<SeckillGoods> util = new ExcelUtil<SeckillGoods>(SeckillGoods.class);
        util.exportExcel(response, list, "秒杀活动商品数据");
    }

    /**
     * 获取秒杀活动商品详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:seckillGoods:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(seckillGoodsService.selectSeckillGoodsById(id));
    }

    /**
     * 新增秒杀活动商品
     */
    @PreAuthorize("@ss.hasPermi('shop:seckillGoods:add')")
    @Log(title = "秒杀活动商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SeckillGoods seckillGoods)
    {
        return toAjax(seckillGoodsService.insertSeckillGoods(seckillGoods));
    }

    /**
     * 修改秒杀活动商品
     */
    @PreAuthorize("@ss.hasPermi('shop:seckillGoods:edit')")
    @Log(title = "秒杀活动商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SeckillGoods seckillGoods)
    {
        return toAjax(seckillGoodsService.updateSeckillGoods(seckillGoods));
    }

    /**
     * 删除秒杀活动商品
     */
    @PreAuthorize("@ss.hasPermi('shop:seckillGoods:remove')")
    @Log(title = "秒杀活动商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(seckillGoodsService.deleteSeckillGoodsByIds(ids));
    }
}

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
import com.ts.shop.domain.GoodsReview;
import com.ts.shop.service.IGoodsReviewService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 评论Controller
 * 
 * @author xu
 * @date 2023-02-24
 */
@RestController
@RequestMapping("/shop/goodsReview")
public class GoodsReviewController extends BaseController
{
    @Autowired
    private IGoodsReviewService goodsReviewService;

    /**
     * 查询评论列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsReview:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsReview goodsReview)
    {
        startPage();
        List<GoodsReview> list = goodsReviewService.selectGoodsReviewList(goodsReview);
        return getDataTable(list);
    }

    /**
     * 导出评论列表
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsReview:export')")
    @Log(title = "评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsReview goodsReview)
    {
        List<GoodsReview> list = goodsReviewService.selectGoodsReviewList(goodsReview);
        ExcelUtil<GoodsReview> util = new ExcelUtil<GoodsReview>(GoodsReview.class);
        util.exportExcel(response, list, "评论数据");
    }

    /**
     * 获取评论详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsReview:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(goodsReviewService.selectGoodsReviewById(id));
    }

    /**
     * 新增评论
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsReview:add')")
    @Log(title = "评论", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsReview goodsReview)
    {
        return toAjax(goodsReviewService.insertGoodsReview(goodsReview));
    }

    /**
     * 修改评论
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsReview:edit')")
    @Log(title = "评论", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsReview goodsReview)
    {
        return toAjax(goodsReviewService.updateGoodsReview(goodsReview));
    }

    /**
     * 删除评论
     */
    @PreAuthorize("@ss.hasPermi('shop:goodsReview:remove')")
    @Log(title = "评论", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(goodsReviewService.deleteGoodsReviewByIds(ids));
    }
}

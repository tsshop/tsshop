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
import com.ts.shop.domain.Banner;
import com.ts.shop.service.IBannerService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 用户首页轮播图片Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/banner")
public class BannerController extends BaseController
{
    @Autowired
    private IBannerService bannerService;

    /**
     * 查询用户首页轮播图片列表
     */
    @PreAuthorize("@ss.hasPermi('shop:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(Banner banner)
    {
        startPage();
        List<Banner> list = bannerService.selectBannerList(banner);
        return getDataTable(list);
    }

    /**
     * 导出用户首页轮播图片列表
     */
    @PreAuthorize("@ss.hasPermi('shop:banner:export')")
    @Log(title = "用户首页轮播图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Banner banner)
    {
        List<Banner> list = bannerService.selectBannerList(banner);
        ExcelUtil<Banner> util = new ExcelUtil<Banner>(Banner.class);
        util.exportExcel(response, list, "用户首页轮播图片数据");
    }

    /**
     * 获取用户首页轮播图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:banner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bannerService.selectBannerById(id));
    }

    /**
     * 新增用户首页轮播图片
     */
    @PreAuthorize("@ss.hasPermi('shop:banner:add')")
    @Log(title = "用户首页轮播图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Banner banner)
    {
        return toAjax(bannerService.insertBanner(banner));
    }

    /**
     * 修改用户首页轮播图片
     */
    @PreAuthorize("@ss.hasPermi('shop:banner:edit')")
    @Log(title = "用户首页轮播图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Banner banner)
    {
        return toAjax(bannerService.updateBanner(banner));
    }

    /**
     * 删除用户首页轮播图片
     */
    @PreAuthorize("@ss.hasPermi('shop:banner:remove')")
    @Log(title = "用户首页轮播图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bannerService.deleteBannerByIds(ids));
    }
}

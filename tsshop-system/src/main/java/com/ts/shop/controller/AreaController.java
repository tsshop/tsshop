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
import com.ts.shop.domain.Area;
import com.ts.shop.service.IAreaService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 地址Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/area")
public class AreaController extends BaseController
{
    @Autowired
    private IAreaService areaService;

    /**
     * 查询地址列表
     */
    @PreAuthorize("@ss.hasPermi('shop:area:list')")
    @GetMapping("/list")
    public TableDataInfo list(Area area)
    {
        startPage();
        List<Area> list = areaService.selectAreaList(area);
        return getDataTable(list);
    }

    /**
     * 导出地址列表
     */
    @PreAuthorize("@ss.hasPermi('shop:area:export')")
    @Log(title = "地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Area area)
    {
        List<Area> list = areaService.selectAreaList(area);
        ExcelUtil<Area> util = new ExcelUtil<Area>(Area.class);
        util.exportExcel(response, list, "地址数据");
    }

    /**
     * 获取地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:area:query')")
    @GetMapping(value = "/{areaId}")
    public AjaxResult getInfo(@PathVariable("areaId") Long areaId)
    {
        return success(areaService.selectAreaByAreaId(areaId));
    }

    /**
     * 新增地址
     */
    @PreAuthorize("@ss.hasPermi('shop:area:add')")
    @Log(title = "地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Area area)
    {
        return toAjax(areaService.insertArea(area));
    }

    /**
     * 修改地址
     */
    @PreAuthorize("@ss.hasPermi('shop:area:edit')")
    @Log(title = "地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Area area)
    {
        return toAjax(areaService.updateArea(area));
    }

    /**
     * 删除地址
     */
    @PreAuthorize("@ss.hasPermi('shop:area:remove')")
    @Log(title = "地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{areaIds}")
    public AjaxResult remove(@PathVariable Long[] areaIds)
    {
        return toAjax(areaService.deleteAreaByAreaIds(areaIds));
    }
}

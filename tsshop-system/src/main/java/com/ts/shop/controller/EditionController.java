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
import com.ts.shop.domain.Edition;
import com.ts.shop.service.IEditionService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 版本Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/edition")
public class EditionController extends BaseController
{
    @Autowired
    private IEditionService editionService;

    /**
     * 查询版本列表
     */
    @PreAuthorize("@ss.hasPermi('shop:edition:list')")
    @GetMapping("/list")
    public TableDataInfo list(Edition edition)
    {
        startPage();
        List<Edition> list = editionService.selectEditionList(edition);
        return getDataTable(list);
    }

    /**
     * 导出版本列表
     */
    @PreAuthorize("@ss.hasPermi('shop:edition:export')")
    @Log(title = "版本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Edition edition)
    {
        List<Edition> list = editionService.selectEditionList(edition);
        ExcelUtil<Edition> util = new ExcelUtil<Edition>(Edition.class);
        util.exportExcel(response, list, "版本数据");
    }

    /**
     * 获取版本详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:edition:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(editionService.selectEditionById(id));
    }

    /**
     * 新增版本
     */
    @PreAuthorize("@ss.hasPermi('shop:edition:add')")
    @Log(title = "版本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Edition edition)
    {
        return toAjax(editionService.insertEdition(edition));
    }

    /**
     * 修改版本
     */
    @PreAuthorize("@ss.hasPermi('shop:edition:edit')")
    @Log(title = "版本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Edition edition)
    {
        return toAjax(editionService.updateEdition(edition));
    }

    /**
     * 删除版本
     */
    @PreAuthorize("@ss.hasPermi('shop:edition:remove')")
    @Log(title = "版本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(editionService.deleteEditionByIds(ids));
    }
}

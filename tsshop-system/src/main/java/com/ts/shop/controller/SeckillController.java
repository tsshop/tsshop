package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ts.shop.domain.param.SeckillDto;
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
import com.ts.shop.domain.Seckill;
import com.ts.shop.service.ISeckillService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 秒杀活动Controller
 * 
 * @author xu
 * @date 2023-02-21
 */
@RestController
@RequestMapping("/shop/seckill")
public class SeckillController extends BaseController
{
    @Autowired
    private ISeckillService seckillService;

    /**
     * 查询秒杀活动列表
     */
    @PreAuthorize("@ss.hasPermi('shop:seckill:list')")
    @GetMapping("/list")
    public TableDataInfo list(Seckill seckill)
    {
        startPage();
        List<Seckill> list = seckillService.selectSeckillList(seckill);
        return getDataTable(list);
    }

    /**
     * 导出秒杀活动列表
     */
    @PreAuthorize("@ss.hasPermi('shop:seckill:export')")
    @Log(title = "秒杀活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Seckill seckill)
    {
        List<Seckill> list = seckillService.selectSeckillList(seckill);
        ExcelUtil<Seckill> util = new ExcelUtil<Seckill>(Seckill.class);
        util.exportExcel(response, list, "秒杀活动数据");
    }

    /**
     * 获取秒杀活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:seckill:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(seckillService.selectSeckillById(id));
    }

    /**
     * 新增秒杀活动
     */
    @PreAuthorize("@ss.hasPermi('shop:seckill:add')")
    @Log(title = "秒杀活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SeckillDto seckill)
    {
        return toAjax(seckillService.insert(seckill));
    }

    /**
     * 修改秒杀活动
     */
    @PreAuthorize("@ss.hasPermi('shop:seckill:edit')")
    @Log(title = "秒杀活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SeckillDto dto)
    {
        return toAjax(seckillService.update(dto));
    }
    //开启/关闭活动

    @PreAuthorize("@ss.hasPermi('shop:seckill:edit')")
    @Log(title = "秒杀活动", businessType = BusinessType.UPDATE)
    @PostMapping("/onOff")
    public AjaxResult onOff(@RequestBody SeckillDto dto)
    {
        return toAjax(seckillService.onOff(dto));
    }

    /**
     * 删除秒杀活动
     */
    @PreAuthorize("@ss.hasPermi('shop:seckill:remove')")
    @Log(title = "秒杀活动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(seckillService.deleteSeckillByIds(ids));
    }
}

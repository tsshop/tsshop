package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
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
import com.ts.shop.domain.Config;
import com.ts.shop.service.IConfigService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 配置Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/config")
public class ConfigController extends BaseController
{
    @Autowired
    private IConfigService configService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 查询配置列表
     */
    @PreAuthorize("@ss.hasPermi('shop:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(Config config)
    {
        startPage();
        List<Config> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    /**
     * 导出配置列表
     */
    @PreAuthorize("@ss.hasPermi('shop:config:export')")
    @Log(title = "配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Config config)
    {
        List<Config> list = configService.selectConfigList(config);
        ExcelUtil<Config> util = new ExcelUtil<Config>(Config.class);
        util.exportExcel(response, list, "配置数据");
    }

    /**
     * 获取配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(configService.selectConfigById(id));
    }

    /**
     * 新增配置
     */
    @PreAuthorize("@ss.hasPermi('shop:config:add')")
    @Log(title = "配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Config config)
    {
        redisTemplate.opsForValue().set("config:" + config.getConfigKey(), config.getConfigValue());
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改配置
     */
    @PreAuthorize("@ss.hasPermi('shop:config:edit')")
    @Log(title = "配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Config config)
    {
        redisTemplate.opsForValue().set("config:" + config.getConfigKey(), config.getConfigValue());
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除配置
     */
    @PreAuthorize("@ss.hasPermi('shop:config:remove')")
    @Log(title = "配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(configService.deleteConfigByIds(ids));
    }
}

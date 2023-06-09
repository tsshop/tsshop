package com.ts.shop.controller;

import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ts.shop.domain.Goods;
import com.ts.shop.domain.TsshopConfig;
import com.ts.shop.domain.dto.OrderExpressDTO;
import com.ts.shop.domain.express.ExpressService;
import com.ts.shop.domain.express.config.ExpressProperties;
import com.ts.shop.domain.express.dao.ExpressDTO;
import com.ts.shop.domain.vo.ExpressConfigVo;
import com.ts.shop.enmus.ExpressConfigEnmu;
import com.ts.shop.mapper.TsshopConfigMapper;
import com.ts.shop.service.IGoodsService;
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
import com.ts.shop.domain.Order;
import com.ts.shop.service.IOrderService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 订单Controller
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/order")
public class OrderController extends BaseController
{
    @Autowired
    private IOrderService orderService;

    @Resource
    TsshopConfigMapper tsshopConfigMapper;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('shop:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(Order order)
    {
        startPage();
        List<Order> list = orderService.selectOrderList(order);
        list.forEach(item -> {
            Goods goods = Optional.ofNullable(goodsService.selectGoodsById(item.getGoodsId())).orElseGet( () -> new Goods());
            item.setGoodsName(goods.getName());
        });
        return getDataTable(list);
    }

    @GetMapping("/count")
    public AjaxResult countOrderInfo() {
        return orderService.getCountOrderInfo();
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('shop:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Order order)
    {
        List<Order> list = orderService.selectOrderList(order);
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderService.selectOrderById(id));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('shop:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Order order)
    {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('shop:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Order order)
    {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('shop:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderService.deleteOrderByIds(ids));
    }

    /**
     * 查询物流
     */
    @PostMapping("/expressInfo")
    public AjaxResult expressInfo(@RequestBody OrderExpressDTO order) {
        ExpressConfigVo expressConfigVo = getKdiniaoConfig();
        ExpressService expressService = new ExpressService(expressConfigVo.getReqUrl());
        ExpressProperties expressProperties = new ExpressProperties();
        expressProperties.setAppId(expressConfigVo.getAppId());
        expressProperties.setEnable(Boolean.parseBoolean(expressConfigVo.getEnable()));
        expressProperties.setAppKey(expressConfigVo.getAppKey());
        expressService.setProperties(expressProperties);
        ExpressDTO expressDTO = expressService.getExpressInfo(order.getOrderNo(),order.getExpress(),order.getExpressNo());
        expressDTO.setOrderStatus(order.getOrderNo());
        return AjaxResult.success(expressDTO);
    }


    private ExpressConfigVo getKdiniaoConfig() {
        ExpressConfigVo expressConfigVo = new ExpressConfigVo();
        TsshopConfig tsshopConfig = getConfigByCode(ExpressConfigEnmu.KDNIAO_APPID.getCode());
        TsshopConfig tsshopConfigAppKey = getConfigByCode(ExpressConfigEnmu.KDNIAO_APPKEY.getCode());
        TsshopConfig tsshopConfigEnable = getConfigByCode(ExpressConfigEnmu.KDNIAO_ENABLE.getCode());
        TsshopConfig tsshopConfigReqUrl = getConfigByCode(ExpressConfigEnmu.KDNIAO_REQURL.getCode());
        if (tsshopConfig != null) {
            expressConfigVo.setAppId(tsshopConfig.getConfigValue());
        }else {
            throw new RuntimeException("快递信息暂无配置");
        }
        if (tsshopConfigAppKey != null) {
            expressConfigVo.setAppKey(tsshopConfigAppKey.getConfigValue());
        }else {
            throw new RuntimeException("快递信息暂无配置");
        }
        if (tsshopConfigEnable != null) {
            expressConfigVo.setEnable(tsshopConfigEnable.getConfigValue());
        }else {
            throw new RuntimeException("快递信息暂无配置");
        }
        if (tsshopConfigReqUrl != null) {
            expressConfigVo.setReqUrl(tsshopConfigReqUrl.getConfigValue());
        } else {
            throw new RuntimeException("快递信息暂无配置");
        }
        return expressConfigVo;
    }
    private TsshopConfig getConfigByCode(String configCode) {
        return tsshopConfigMapper.selectOne(new LambdaQueryWrapper<TsshopConfig>().eq(TsshopConfig::getConfigKey, configCode));
    }
}

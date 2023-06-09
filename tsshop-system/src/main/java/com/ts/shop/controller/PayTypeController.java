package com.ts.shop.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.ts.common.annotation.Log;
import com.ts.common.core.controller.BaseController;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.core.page.TableDataInfo;
import com.ts.common.enums.BusinessType;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.shop.domain.PayInterface;
import com.ts.shop.domain.PayThoroughfare;
import com.ts.shop.domain.PayType;
import com.ts.shop.domain.pay.PayEnums;
import com.ts.shop.service.IPayInterfaceService;
import com.ts.shop.service.IPayThoroughfareService;
import com.ts.shop.service.IPayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 支付方式Controller
 *
 * @author xu
 * @date 2023-04-25
 */
@RestController
@RequestMapping("/pay/type")
public class PayTypeController extends BaseController
{
    @Autowired
    private IPayTypeService payTypeService;

    @Autowired
    private IPayInterfaceService payInterfaceService;

    @Autowired
    private IPayThoroughfareService payThoroughfareService;

    @GetMapping("/getType")
    public AjaxResult getType () {
        Map<Object, Object> result = new HashMap<>();
        Arrays.stream(PayEnums.PayTypeEnum.values()).forEach(item -> {
            result.put(item.getCode(), item.getMsg());
        });
        return AjaxResult.success(result);
    }

    /**
     * 查询支付方式列表
     */
    @PreAuthorize("@ss.hasPermi('pay:type:list')")
    @GetMapping("/list")
    public TableDataInfo list(PayType payType)
    {
        startPage();
        List<PayType> list = payTypeService.selectPayTypeList(payType);
        return getDataTable(list);
    }

    /**
     * 导出支付方式列表
     */
    @PreAuthorize("@ss.hasPermi('pay:type:export')")
    @Log(title = "支付方式", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayType payType)
    {
        List<PayType> list = payTypeService.selectPayTypeList(payType);
        ExcelUtil<PayType> util = new ExcelUtil<PayType>(PayType.class);
        util.exportExcel(response, list, "支付方式数据");
    }

    /**
     * 获取支付方式详细信息
     */
    @PreAuthorize("@ss.hasPermi('pay:type:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(payTypeService.selectPayTypeById(id));
    }

    /**
     * 新增支付方式
     */
    @PreAuthorize("@ss.hasPermi('pay:type:add')")
    @Log(title = "支付方式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayType payType)
    {
        PayType param = new PayType();
        param.setType(payType.getType());
        List<PayType> payTypeList = payTypeService.selectPayTypeList(param);
        if (CollectionUtil.isNotEmpty(payTypeList)){
            return AjaxResult.error("已存在该支付方式，无法重复添加");
        }
        return toAjax(payTypeService.insertPayType(payType));
    }

    /**
     * 修改支付方式
     */
    @PreAuthorize("@ss.hasPermi('pay:type:edit')")
    @Log(title = "支付方式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayType payType)
    {
        return toAjax(payTypeService.updatePayType(payType));
    }

    /**
     * 删除支付方式
     */
    @PreAuthorize("@ss.hasPermi('pay:type:remove')")
    @Log(title = "支付方式", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        PayType payType = payTypeService.selectPayTypeById(id);
        Set<String> check = new HashSet<>();
        List<PayInterface> payInterfaceList = payInterfaceService.selectPayInterfaceList(new PayInterface());
        payInterfaceList.forEach(item -> {
            List<String> list = (List<String>) JSONArray.parse(item.getPayTypes());
            check.addAll(list);
        });

        List<PayThoroughfare> payThoroughfareList = payThoroughfareService.selectPayThoroughfareList(new PayThoroughfare());
        payThoroughfareList.forEach(item -> {
            List<String> list = (List<String>)JSONArray.parse(item.getPayTypes());
            check.addAll(list);
        });
        if (check.contains(payType.getType())){
            return AjaxResult.error("无法删除已被使用的支付方式");
        }
        return toAjax(payTypeService.deletePayTypeById(id));
    }
}

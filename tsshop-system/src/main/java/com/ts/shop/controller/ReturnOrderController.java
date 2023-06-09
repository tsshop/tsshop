package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ts.shop.domain.param.ReturnParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.ts.shop.domain.ReturnOrder;
import com.ts.shop.service.IReturnOrderService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 退款Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/returnOrder")
public class ReturnOrderController extends BaseController
{
    @Autowired
    private IReturnOrderService returnOrderService;

    /**
     * 查询退款列表
     */
    @PreAuthorize("@ss.hasPermi('shop:returnOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(ReturnOrder returnOrder)
    {
        startPage();
        List<ReturnOrder> list = returnOrderService.selectReturnOrderList(returnOrder);
        return getDataTable(list);
    }

    /**
     * 导出退款列表
     */
    @PreAuthorize("@ss.hasPermi('shop:returnOrder:export')")
    @Log(title = "退款", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReturnOrder returnOrder)
    {
        List<ReturnOrder> list = returnOrderService.selectReturnOrderList(returnOrder);
        ExcelUtil<ReturnOrder> util = new ExcelUtil<ReturnOrder>(ReturnOrder.class);
        util.exportExcel(response, list, "退款数据");
    }

    /**
     * 获取退款详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:returnOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(returnOrderService.selectReturnOrderById(id));
    }

    /**
     * 新增退款
     */
    @PreAuthorize("@ss.hasPermi('shop:returnOrder:add')")
    @Log(title = "退款", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ReturnOrder returnOrder)
    {
        return toAjax(returnOrderService.insertReturnOrder(returnOrder));
    }

    /**
     * 修改退款
     */
    @PreAuthorize("@ss.hasPermi('shop:returnOrder:edit')")
    @Log(title = "退款", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReturnOrder returnOrder)
    {
        return toAjax(returnOrderService.updateReturnOrder(returnOrder));
    }

    /**
     * 删除退款
     */
    @PreAuthorize("@ss.hasPermi('shop:returnOrder:remove')")
    @Log(title = "退款", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(returnOrderService.deleteReturnOrderByIds(ids));
    }

    @GetMapping("/getGoods")
    @Log(title = "详情")
    @PreAuthorize("@ss.hasPermi('shop:returnOrder:list')")
    public ResponseEntity<Object> getGoods(Long id){
        return new ResponseEntity<>(returnOrderService.getGoods(id), HttpStatus.OK);
    }
    @PostMapping("/examine")
    @Log(title = "审核")
    @PreAuthorize("@ss.hasPermi('shop:returnOrder:edit')")
    public ResponseEntity<Object> examine(@Validated @RequestBody ReturnParam param) throws Exception {
        returnOrderService.examine(param);
        return new ResponseEntity<>("",HttpStatus.OK);
    }

}

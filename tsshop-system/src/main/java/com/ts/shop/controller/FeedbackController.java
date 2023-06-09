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
import com.ts.shop.domain.Feedback;
import com.ts.shop.service.IFeedbackService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 反馈Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/shop/feedback")
public class FeedbackController extends BaseController
{
    @Autowired
    private IFeedbackService feedbackService;

    /**
     * 查询反馈列表
     */
    @PreAuthorize("@ss.hasPermi('shop:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(Feedback feedback)
    {
        startPage();
        List<Feedback> list = feedbackService.selectFeedbackList(feedback);
        return getDataTable(list);
    }

    /**
     * 导出反馈列表
     */
    @PreAuthorize("@ss.hasPermi('shop:feedback:export')")
    @Log(title = "反馈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Feedback feedback)
    {
        List<Feedback> list = feedbackService.selectFeedbackList(feedback);
        ExcelUtil<Feedback> util = new ExcelUtil<Feedback>(Feedback.class);
        util.exportExcel(response, list, "反馈数据");
    }

    /**
     * 获取反馈详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:feedback:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(feedbackService.selectFeedbackById(id));
    }

    /**
     * 新增反馈
     */
    @PreAuthorize("@ss.hasPermi('shop:feedback:add')")
    @Log(title = "反馈", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Feedback feedback)
    {
        return toAjax(feedbackService.insertFeedback(feedback));
    }

    /**
     * 修改反馈
     */
    @PreAuthorize("@ss.hasPermi('shop:feedback:edit')")
    @Log(title = "反馈", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Feedback feedback)
    {
        return toAjax(feedbackService.updateFeedback(feedback));
    }

    /**
     * 删除反馈
     */
    @PreAuthorize("@ss.hasPermi('shop:feedback:remove')")
    @Log(title = "反馈", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(feedbackService.deleteFeedbackByIds(ids));
    }
}

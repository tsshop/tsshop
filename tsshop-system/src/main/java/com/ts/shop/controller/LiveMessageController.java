package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ts.shop.domain.User;
import com.ts.shop.service.IUserService;
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
import com.ts.shop.domain.LiveMessage;
import com.ts.shop.service.ILiveMessageService;
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 弹幕Controller
 *
 * @author ruoyi
 * @date 2023-06-05
 */
@RestController
@RequestMapping("/shop/message")
public class LiveMessageController extends BaseController
{
    @Autowired
    private ILiveMessageService liveMessageService;

    @Autowired
    private IUserService userService;

    /**
     * 查询弹幕列表
     */
    @PreAuthorize("@ss.hasPermi('shop:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(LiveMessage liveMessage)
    {
        startPage();
        List<LiveMessage> list = liveMessageService.selectLiveMessageList(liveMessage);
        list.forEach(item -> {
            User user = userService.selectUserById(item.getUserId());
            item.setUserName(user.getName());
        });
        return getDataTable(list);
    }

    /**
     * 导出弹幕列表
     */
    @PreAuthorize("@ss.hasPermi('shop:message:export')")
    @Log(title = "弹幕", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LiveMessage liveMessage)
    {
        List<LiveMessage> list = liveMessageService.selectLiveMessageList(liveMessage);
        ExcelUtil<LiveMessage> util = new ExcelUtil<LiveMessage>(LiveMessage.class);
        util.exportExcel(response, list, "弹幕数据");
    }

    /**
     * 获取弹幕详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:message:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(liveMessageService.selectLiveMessageById(id));
    }

    /**
     * 新增弹幕
     */
    @PreAuthorize("@ss.hasPermi('shop:message:add')")
    @Log(title = "弹幕", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LiveMessage liveMessage)
    {
        return toAjax(liveMessageService.insertLiveMessage(liveMessage));
    }

    /**
     * 修改弹幕
     */
    @PreAuthorize("@ss.hasPermi('shop:message:edit')")
    @Log(title = "弹幕", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LiveMessage liveMessage)
    {
        return toAjax(liveMessageService.updateLiveMessage(liveMessage));
    }

    /**
     * 删除弹幕
     */
    @PreAuthorize("@ss.hasPermi('shop:message:remove')")
    @Log(title = "弹幕", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(liveMessageService.deleteLiveMessageByIds(ids));
    }
}

package com.ts.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ts.shop.domain.UserLiveGift;
import com.ts.shop.service.IUserLiveGiftService;
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
import com.ts.common.utils.poi.ExcelUtil;
import com.ts.common.core.page.TableDataInfo;

/**
 * 用户礼物Controller
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
@RestController
@RequestMapping("/liveGift/userGift")
public class UserLiveGiftController extends BaseController
{
    @Autowired
    private IUserLiveGiftService userLiveGiftService;

    /**
     * 查询用户礼物列表
     */
    @PreAuthorize("@ss.hasPermi('liveGift:userGift:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserLiveGift userLiveGift)
    {
        startPage();
        List<UserLiveGift> list = userLiveGiftService.selectUserLiveGiftList(userLiveGift);
        return getDataTable(list);
    }

    /**
     * 导出用户礼物列表
     */
    @PreAuthorize("@ss.hasPermi('liveGift:userGift:export')")
    @Log(title = "用户礼物", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserLiveGift userLiveGift)
    {
        List<UserLiveGift> list = userLiveGiftService.selectUserLiveGiftList(userLiveGift);
        ExcelUtil<UserLiveGift> util = new ExcelUtil<UserLiveGift>(UserLiveGift.class);
        util.exportExcel(response, list, "用户礼物数据");
    }

    /**
     * 获取用户礼物详细信息
     */
    @PreAuthorize("@ss.hasPermi('liveGift:userGift:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userLiveGiftService.selectUserLiveGiftById(id));
    }

    /**
     * 新增用户礼物
     */
    @PreAuthorize("@ss.hasPermi('liveGift:userGift:add')")
    @Log(title = "用户礼物", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserLiveGift userLiveGift)
    {
        return toAjax(userLiveGiftService.insertUserLiveGift(userLiveGift));
    }

    /**
     * 修改用户礼物
     */
    @PreAuthorize("@ss.hasPermi('liveGift:userGift:edit')")
    @Log(title = "用户礼物", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserLiveGift userLiveGift)
    {
        return toAjax(userLiveGiftService.updateUserLiveGift(userLiveGift));
    }

    /**
     * 删除用户礼物
     */
    @PreAuthorize("@ss.hasPermi('liveGift:userGift:remove')")
    @Log(title = "用户礼物", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userLiveGiftService.deleteUserLiveGiftByIds(ids));
    }
}

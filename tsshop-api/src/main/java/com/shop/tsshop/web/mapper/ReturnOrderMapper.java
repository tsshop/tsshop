package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.ReturnOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.vo.ReturnOrderListVo;

import java.util.List;

/**
 * <p>
 * 退款表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-10
 */
public interface ReturnOrderMapper extends BaseMapper<ReturnOrder> {

    List<ReturnOrderListVo> getList(Long userId);
}

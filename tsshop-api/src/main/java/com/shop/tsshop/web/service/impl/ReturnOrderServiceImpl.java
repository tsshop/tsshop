package com.shop.tsshop.web.service.impl;

import com.shop.tsshop.web.model.domain.ReturnOrder;
import com.shop.tsshop.web.mapper.ReturnOrderMapper;
import com.shop.tsshop.web.model.vo.ReturnOrderListVo;
import com.shop.tsshop.web.service.ReturnOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 退款表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-10
 */
@Service
public class ReturnOrderServiceImpl extends ServiceImpl<ReturnOrderMapper, ReturnOrder> implements ReturnOrderService {

    @Override
    public List<ReturnOrderListVo> getList(Long userId) {
        return baseMapper.getList(userId);
    }
}

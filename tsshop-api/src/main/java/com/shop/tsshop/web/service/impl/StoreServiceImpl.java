package com.shop.tsshop.web.service.impl;

import com.shop.tsshop.web.model.domain.Store;
import com.shop.tsshop.web.mapper.StoreMapper;
import com.shop.tsshop.web.service.StoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 店铺表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {

}

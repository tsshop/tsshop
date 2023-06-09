package com.shop.tsshop.web.service.impl;

import com.shop.tsshop.web.model.domain.Area;
import com.shop.tsshop.web.mapper.AreaMapper;
import com.shop.tsshop.web.model.vo.AreaVo;
import com.shop.tsshop.web.service.AreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地址表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Override
    public List<AreaVo> getDistrict() {
        return baseMapper.getDistrict();
    }
}

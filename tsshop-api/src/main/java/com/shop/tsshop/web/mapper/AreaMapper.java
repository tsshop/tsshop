package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.Area;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.vo.AreaVo;

import java.util.List;

/**
 * <p>
 * 地址表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface AreaMapper extends BaseMapper<Area> {

    List<AreaVo> getDistrict();
}

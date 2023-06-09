package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.Area;
import com.shop.tsshop.web.model.vo.AreaVo;

import java.util.List;

/**
 * <p>
 * 地址表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface AreaService extends IService<Area> {

    /**
     * 用户端：查询地址省市县
     * @return                统一返回
     */
    List<AreaVo> getDistrict();
}

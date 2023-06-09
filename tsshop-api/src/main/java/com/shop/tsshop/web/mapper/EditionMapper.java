package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.Edition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.vo.EditionVo;

/**
 * <p>
 * 版本表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
public interface EditionMapper extends BaseMapper<Edition> {

    EditionVo getEdition(Integer type);
}

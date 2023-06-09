package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-06
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

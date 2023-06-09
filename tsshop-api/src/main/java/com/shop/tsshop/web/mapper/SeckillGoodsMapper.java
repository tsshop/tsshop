package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.SeckillGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.vo.SeckillGoodsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * <p>
 * 秒杀活动商品 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-21
 */
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {

    List<SeckillGoodsVo> selectGoodsVo(@Param("seckillId") Long seckillId, @Param("time") int time);

    SeckillGoods getGoodsById(@Param("id") Long id, @Param("k") int k);

    int updateStock(@Param("num") int num,@Param("id") Long id);

    List<SeckillGoods> getGoodsListById(Long id);
}

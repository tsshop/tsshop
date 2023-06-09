package com.ts.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.shop.domain.PayInterface;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 支付接口Mapper接口
 *
 * @author xu
 * @date 2023-04-25
 */
public interface PayInterfaceMapper extends BaseMapper<PayInterface>
{
    /**
     * 查询支付接口
     *
     * @param id 支付接口主键
     * @return 支付接口
     */
    public PayInterface selectPayInterfaceById(Long id);

    /**
     * 查询支付接口列表
     *
     * @param payInterface 支付接口
     * @return 支付接口集合
     */
    public List<PayInterface> selectPayInterfaceList(PayInterface payInterface);

    /**
     * 新增支付接口
     *
     * @param payInterface 支付接口
     * @return 结果
     */
    public int insertPayInterface(PayInterface payInterface);

    /**
     * 修改支付接口
     *
     * @param payInterface 支付接口
     * @return 结果
     */
    public int updatePayInterface(PayInterface payInterface);

    /**
     * 删除支付接口
     *
     * @param id 支付接口主键
     * @return 结果
     */
    public int deletePayInterfaceById(Long id);

    /**
     * 批量删除支付接口
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePayInterfaceByIds(Long[] ids);

    @Select("select count(1) from pay_thoroughfare where interface_id = #{id} ")
    Integer check(Long id);
}

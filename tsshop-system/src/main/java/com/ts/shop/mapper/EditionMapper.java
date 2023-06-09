package com.ts.shop.mapper;

import java.util.List;
import com.ts.shop.domain.Edition;

/**
 * 版本Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface EditionMapper 
{
    /**
     * 查询版本
     * 
     * @param id 版本主键
     * @return 版本
     */
    public Edition selectEditionById(Long id);

    /**
     * 查询版本列表
     * 
     * @param edition 版本
     * @return 版本集合
     */
    public List<Edition> selectEditionList(Edition edition);

    /**
     * 新增版本
     * 
     * @param edition 版本
     * @return 结果
     */
    public int insertEdition(Edition edition);

    /**
     * 修改版本
     * 
     * @param edition 版本
     * @return 结果
     */
    public int updateEdition(Edition edition);

    /**
     * 删除版本
     * 
     * @param id 版本主键
     * @return 结果
     */
    public int deleteEditionById(Long id);

    /**
     * 批量删除版本
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEditionByIds(Long[] ids);
}

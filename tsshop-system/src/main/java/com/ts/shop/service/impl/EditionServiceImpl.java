package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.EditionMapper;
import com.ts.shop.domain.Edition;
import com.ts.shop.service.IEditionService;

/**
 * 版本Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class EditionServiceImpl implements IEditionService 
{
    @Autowired
    private EditionMapper editionMapper;

    /**
     * 查询版本
     * 
     * @param id 版本主键
     * @return 版本
     */
    @Override
    public Edition selectEditionById(Long id)
    {
        return editionMapper.selectEditionById(id);
    }

    /**
     * 查询版本列表
     * 
     * @param edition 版本
     * @return 版本
     */
    @Override
    public List<Edition> selectEditionList(Edition edition)
    {
        return editionMapper.selectEditionList(edition);
    }

    /**
     * 新增版本
     * 
     * @param edition 版本
     * @return 结果
     */
    @Override
    public int insertEdition(Edition edition)
    {
        edition.setCreateTime(DateUtils.getNowDate());
        return editionMapper.insertEdition(edition);
    }

    /**
     * 修改版本
     * 
     * @param edition 版本
     * @return 结果
     */
    @Override
    public int updateEdition(Edition edition)
    {
        edition.setUpdateTime(DateUtils.getNowDate());
        return editionMapper.updateEdition(edition);
    }

    /**
     * 批量删除版本
     * 
     * @param ids 需要删除的版本主键
     * @return 结果
     */
    @Override
    public int deleteEditionByIds(Long[] ids)
    {
        return editionMapper.deleteEditionByIds(ids);
    }

    /**
     * 删除版本信息
     * 
     * @param id 版本主键
     * @return 结果
     */
    @Override
    public int deleteEditionById(Long id)
    {
        return editionMapper.deleteEditionById(id);
    }
}

package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.TsshopConfigStorage;
import com.ts.shop.mapper.TsshopConfigStorageMapper;
import com.ts.shop.service.ITsshopConfigStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 存储配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-06
 */
@Service
public class TsshopConfigStorageServiceImpl implements ITsshopConfigStorageService
{
    @Autowired
    private TsshopConfigStorageMapper tsshopConfigStorageMapper;

    /**
     * 查询存储配置
     * 
     * @param id 存储配置主键
     * @return 存储配置
     */
    @Override
    public TsshopConfigStorage selectTsshopConfigStorageById(Long id)
    {
        return tsshopConfigStorageMapper.selectTsshopConfigStorageById(id);
    }

    /**
     * 查询存储配置列表
     * 
     * @param tsshopConfigStorage 存储配置
     * @return 存储配置
     */
    @Override
    public List<TsshopConfigStorage> selectTsshopConfigStorageList(TsshopConfigStorage tsshopConfigStorage)
    {
        return tsshopConfigStorageMapper.selectTsshopConfigStorageList(tsshopConfigStorage);
    }

    /**
     * 新增存储配置
     * 
     * @param tsshopConfigStorage 存储配置
     * @return 结果
     */
    @Override
    public int insertTsshopConfigStorage(TsshopConfigStorage tsshopConfigStorage)
    {
        tsshopConfigStorage.setCreateTime(DateUtils.getNowDate());
        return tsshopConfigStorageMapper.insertTsshopConfigStorage(tsshopConfigStorage);
    }

    /**
     * 修改存储配置
     * 
     * @param tsshopConfigStorage 存储配置
     * @return 结果
     */
    @Override
    public int updateTsshopConfigStorage(TsshopConfigStorage tsshopConfigStorage)
    {
        tsshopConfigStorage.setUpdateTime(DateUtils.getNowDate());
        return tsshopConfigStorageMapper.updateTsshopConfigStorage(tsshopConfigStorage);
    }

    /**
     * 批量删除存储配置
     * 
     * @param ids 需要删除的存储配置主键
     * @return 结果
     */
    @Override
    public int deleteTsshopConfigStorageByIds(Long[] ids)
    {
        return tsshopConfigStorageMapper.deleteTsshopConfigStorageByIds(ids);
    }

    /**
     * 删除存储配置信息
     * 
     * @param id 存储配置主键
     * @return 结果
     */
    @Override
    public int deleteTsshopConfigStorageById(Long id)
    {
        return tsshopConfigStorageMapper.deleteTsshopConfigStorageById(id);
    }
}

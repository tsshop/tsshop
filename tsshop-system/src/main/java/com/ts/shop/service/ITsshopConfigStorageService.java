package com.ts.shop.service;

import java.util.List;

import com.ts.shop.domain.TsshopConfigStorage;

/**
 * 存储配置Service接口
 * 
 * @author ruoyi
 * @date 2023-06-06
 */
public interface ITsshopConfigStorageService 
{
    /**
     * 查询存储配置
     * 
     * @param id 存储配置主键
     * @return 存储配置
     */
    public TsshopConfigStorage selectTsshopConfigStorageById(Long id);

    /**
     * 查询存储配置列表
     * 
     * @param tsshopConfigStorage 存储配置
     * @return 存储配置集合
     */
    public List<TsshopConfigStorage> selectTsshopConfigStorageList(TsshopConfigStorage tsshopConfigStorage);

    /**
     * 新增存储配置
     * 
     * @param tsshopConfigStorage 存储配置
     * @return 结果
     */
    public int insertTsshopConfigStorage(TsshopConfigStorage tsshopConfigStorage);

    /**
     * 修改存储配置
     * 
     * @param tsshopConfigStorage 存储配置
     * @return 结果
     */
    public int updateTsshopConfigStorage(TsshopConfigStorage tsshopConfigStorage);

    /**
     * 批量删除存储配置
     * 
     * @param ids 需要删除的存储配置主键集合
     * @return 结果
     */
    public int deleteTsshopConfigStorageByIds(Long[] ids);

    /**
     * 删除存储配置信息
     * 
     * @param id 存储配置主键
     * @return 结果
     */
    public int deleteTsshopConfigStorageById(Long id);
}

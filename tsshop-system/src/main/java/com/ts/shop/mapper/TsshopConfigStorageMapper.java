package com.ts.shop.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.shop.domain.TsshopConfigStorage;

/**
 * 存储配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-06
 */
public interface TsshopConfigStorageMapper extends BaseMapper<TsshopConfigStorage>
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
     * 删除存储配置
     * 
     * @param id 存储配置主键
     * @return 结果
     */
    public int deleteTsshopConfigStorageById(Long id);

    /**
     * 批量删除存储配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTsshopConfigStorageByIds(Long[] ids);
}

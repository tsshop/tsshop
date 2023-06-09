package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.Config;

/**
 * 配置Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IConfigService 
{
    /**
     * 查询配置
     * 
     * @param id 配置主键
     * @return 配置
     */
    public Config selectConfigById(Long id);

    /**
     * 查询配置列表
     * 
     * @param config 配置
     * @return 配置集合
     */
    public List<Config> selectConfigList(Config config);

    /**
     * 新增配置
     * 
     * @param config 配置
     * @return 结果
     */
    public int insertConfig(Config config);

    /**
     * 修改配置
     * 
     * @param config 配置
     * @return 结果
     */
    public int updateConfig(Config config);

    /**
     * 批量删除配置
     * 
     * @param ids 需要删除的配置主键集合
     * @return 结果
     */
    public int deleteConfigByIds(Long[] ids);

    /**
     * 删除配置信息
     * 
     * @param id 配置主键
     * @return 结果
     */
    public int deleteConfigById(Long id);
}

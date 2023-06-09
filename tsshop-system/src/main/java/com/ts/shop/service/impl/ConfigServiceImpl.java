package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.ConfigMapper;
import com.ts.shop.domain.Config;
import com.ts.shop.service.IConfigService;

/**
 * 配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class ConfigServiceImpl implements IConfigService 
{
    @Autowired
    private ConfigMapper configMapper;

    /**
     * 查询配置
     * 
     * @param id 配置主键
     * @return 配置
     */
    @Override
    public Config selectConfigById(Long id)
    {
        return configMapper.selectConfigById(id);
    }

    /**
     * 查询配置列表
     * 
     * @param config 配置
     * @return 配置
     */
    @Override
    public List<Config> selectConfigList(Config config)
    {
        return configMapper.selectConfigList(config);
    }

    /**
     * 新增配置
     * 
     * @param config 配置
     * @return 结果
     */
    @Override
    public int insertConfig(Config config)
    {
        config.setCreateTime(DateUtils.getNowDate());
        return configMapper.insertConfig(config);
    }

    /**
     * 修改配置
     * 
     * @param config 配置
     * @return 结果
     */
    @Override
    public int updateConfig(Config config)
    {
        config.setUpdateTime(DateUtils.getNowDate());
        return configMapper.updateConfig(config);
    }

    /**
     * 批量删除配置
     * 
     * @param ids 需要删除的配置主键
     * @return 结果
     */
    @Override
    public int deleteConfigByIds(Long[] ids)
    {

        return configMapper.deleteConfigByIds(ids);
    }

    /**
     * 删除配置信息
     * 
     * @param id 配置主键
     * @return 结果
     */
    @Override
    public int deleteConfigById(Long id)
    {
        return configMapper.deleteConfigById(id);
    }
}

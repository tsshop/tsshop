package com.shop.tsshop.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.tsshop.web.model.domain.Config;
import com.shop.tsshop.web.mapper.ConfigMapper;
import com.shop.tsshop.web.service.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 配置 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {
    @Autowired
    RedisService redisService;

    public String getConfig(String key){
        if(redisService.hasKey(key)){
            return redisService.getString(key);
        }
        Config config=getOne(new LambdaQueryWrapper<Config>().eq(Config::getConfigKey,key));
        redisService.saveCode(key,config.getConfigValue());
        return config.getConfigValue();
    }

    public Map<String,String> getConfigList(){
        Map<String,String> config=baseMapper.selectList(new LambdaQueryWrapper<Config>().eq(Config::getIsFront,1)).stream().collect(Collectors.toMap(Config::getConfigKey, Config::getConfigValue));
        return config;
    }
}

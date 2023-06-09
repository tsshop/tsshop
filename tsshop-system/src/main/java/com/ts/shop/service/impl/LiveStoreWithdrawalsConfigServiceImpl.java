package com.ts.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.core.redis.RedisCache;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.LiveStoreWithdrawalsConfig;
import com.ts.shop.domain.PayThoroughfare;
import com.ts.shop.domain.PayType;
import com.ts.shop.domain.vo.AmountTypeVo;
import com.ts.shop.mapper.LiveStoreWithdrawalsConfigMapper;
import com.ts.shop.mapper.PayThoroughfareMapper;
import com.ts.shop.mapper.PayTypeMapper;
import com.ts.shop.service.ILiveStoreWithdrawalsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 店铺提现配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
@Service
public class LiveStoreWithdrawalsConfigServiceImpl implements ILiveStoreWithdrawalsConfigService
{
    @Autowired
    private LiveStoreWithdrawalsConfigMapper liveStoreWithdrawalsConfigMapper;

    @Autowired
    private PayThoroughfareMapper payThoroughfareMapper;

    @Autowired
    private PayTypeMapper payTypeMapper;

    /**
     * 查询店铺提现配置
     * 
     * @param id 店铺提现配置主键
     * @return 店铺提现配置
     */
    @Override
    public LiveStoreWithdrawalsConfig selectLiveStoreWithdrawalsConfigById(Long id)
    {
        return liveStoreWithdrawalsConfigMapper.selectLiveStoreWithdrawalsConfigById(id);
    }

    /**
     * 查询店铺提现配置列表
     * 
     * @param liveStoreWithdrawalsConfig 店铺提现配置
     * @return 店铺提现配置
     */
    @Override
    public List<LiveStoreWithdrawalsConfig> selectLiveStoreWithdrawalsConfigList(LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig)
    {
        return liveStoreWithdrawalsConfigMapper.selectLiveStoreWithdrawalsConfigList(liveStoreWithdrawalsConfig);
    }

    /**
     * 新增店铺提现配置
     * 
     * @param liveStoreWithdrawalsConfig 店铺提现配置
     * @return 结果
     */
    @Override
    public int insertLiveStoreWithdrawalsConfig(LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig)
    {
        liveStoreWithdrawalsConfig.setCreateTime(DateUtils.getNowDate());
        return liveStoreWithdrawalsConfigMapper.insertLiveStoreWithdrawalsConfig(liveStoreWithdrawalsConfig);
    }

    /**
     * 修改店铺提现配置
     * 
     * @param liveStoreWithdrawalsConfig 店铺提现配置
     * @return 结果
     */
    @Override
    public int updateLiveStoreWithdrawalsConfig(LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig)
    {
        liveStoreWithdrawalsConfig.setUpdateTime(DateUtils.getNowDate());
        return liveStoreWithdrawalsConfigMapper.updateLiveStoreWithdrawalsConfig(liveStoreWithdrawalsConfig);
    }

    /**
     * 批量删除店铺提现配置
     * 
     * @param ids 需要删除的店铺提现配置主键
     * @return 结果
     */
    @Override
    public int deleteLiveStoreWithdrawalsConfigByIds(Long[] ids)
    {
        return liveStoreWithdrawalsConfigMapper.deleteLiveStoreWithdrawalsConfigByIds(ids);
    }

    /**
     * 删除店铺提现配置信息
     * 
     * @param id 店铺提现配置主键
     * @return 结果
     */
    @Override
    public int deleteLiveStoreWithdrawalsConfigById(Long id)
    {
        return liveStoreWithdrawalsConfigMapper.deleteLiveStoreWithdrawalsConfigById(id);
    }

    @Override
    public AjaxResult getAccountType() {
        List<PayThoroughfare> payThoroughfares = payThoroughfareMapper.selectList(new LambdaQueryWrapper<PayThoroughfare>().eq(PayThoroughfare::getSupportWithdrawals, false));

        List<PayType> payTypeList = Optional.ofNullable(payTypeMapper.selectList(new LambdaQueryWrapper<PayType>())).orElseGet(() -> new ArrayList<>());
        Map<String,PayType> payTypeMap = payTypeList.stream().collect(Collectors.toMap(PayType::getType , item -> item));
        Map<Long,PayThoroughfare> payThoroughfareMap = payThoroughfares.stream().collect(Collectors.toMap(PayThoroughfare::getId, item -> item));
        List<AmountTypeVo> amountTypeVos = new ArrayList<>();
        payThoroughfareMap.forEach((key,value) -> {
            List<String> list = (List<String>) JSONArray.parse(value.getPayTypes());
            list.forEach(item -> {
                PayType payType = payTypeMap.get(item);
                if (ObjectUtil.isNotEmpty(payType)){
                    AmountTypeVo amountTypeVo = new AmountTypeVo()
                            .setPayThoroughfareId(value.getId())
                            .setType(payType.getType())
                            .setLogo(payType.getLogo())
                            .setName(payType.getName());
                    amountTypeVos.add(amountTypeVo);
                }
            });
        });
        return AjaxResult.success(amountTypeVos);
    }
}

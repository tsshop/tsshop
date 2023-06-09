package com.ts.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.AreaMapper;
import com.ts.shop.domain.Area;
import com.ts.shop.service.IAreaService;

/**
 * 地址Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class AreaServiceImpl implements IAreaService 
{
    @Autowired
    private AreaMapper areaMapper;

    /**
     * 查询地址
     * 
     * @param areaId 地址主键
     * @return 地址
     */
    @Override
    public Area selectAreaByAreaId(Long areaId)
    {
        return areaMapper.selectAreaByAreaId(areaId);
    }

    /**
     * 查询地址列表
     * 
     * @param area 地址
     * @return 地址
     */
    @Override
    public List<Area> selectAreaList(Area area)
    {
        return areaMapper.selectAreaList(area);
    }

    /**
     * 新增地址
     * 
     * @param area 地址
     * @return 结果
     */
    @Override
    public int insertArea(Area area)
    {
        return areaMapper.insertArea(area);
    }

    /**
     * 修改地址
     * 
     * @param area 地址
     * @return 结果
     */
    @Override
    public int updateArea(Area area)
    {
        return areaMapper.updateArea(area);
    }

    /**
     * 批量删除地址
     * 
     * @param areaIds 需要删除的地址主键
     * @return 结果
     */
    @Override
    public int deleteAreaByAreaIds(Long[] areaIds)
    {
        return areaMapper.deleteAreaByAreaIds(areaIds);
    }

    /**
     * 删除地址信息
     * 
     * @param areaId 地址主键
     * @return 结果
     */
    @Override
    public int deleteAreaByAreaId(Long areaId)
    {
        return areaMapper.deleteAreaByAreaId(areaId);
    }
}

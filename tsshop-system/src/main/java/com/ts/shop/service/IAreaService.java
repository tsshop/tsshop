package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.Area;

/**
 * 地址Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IAreaService 
{
    /**
     * 查询地址
     * 
     * @param areaId 地址主键
     * @return 地址
     */
    public Area selectAreaByAreaId(Long areaId);

    /**
     * 查询地址列表
     * 
     * @param area 地址
     * @return 地址集合
     */
    public List<Area> selectAreaList(Area area);

    /**
     * 新增地址
     * 
     * @param area 地址
     * @return 结果
     */
    public int insertArea(Area area);

    /**
     * 修改地址
     * 
     * @param area 地址
     * @return 结果
     */
    public int updateArea(Area area);

    /**
     * 批量删除地址
     * 
     * @param areaIds 需要删除的地址主键集合
     * @return 结果
     */
    public int deleteAreaByAreaIds(Long[] areaIds);

    /**
     * 删除地址信息
     * 
     * @param areaId 地址主键
     * @return 结果
     */
    public int deleteAreaByAreaId(Long areaId);
}

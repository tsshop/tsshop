package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.GoodsReviewMapper;
import com.ts.shop.domain.GoodsReview;
import com.ts.shop.service.IGoodsReviewService;

/**
 * 评论Service业务层处理
 * 
 * @author xu
 * @date 2023-02-24
 */
@Service
public class GoodsReviewServiceImpl implements IGoodsReviewService 
{
    @Autowired
    private GoodsReviewMapper goodsReviewMapper;

    /**
     * 查询评论
     * 
     * @param id 评论主键
     * @return 评论
     */
    @Override
    public GoodsReview selectGoodsReviewById(Long id)
    {
        return goodsReviewMapper.selectGoodsReviewById(id);
    }

    /**
     * 查询评论列表
     * 
     * @param goodsReview 评论
     * @return 评论
     */
    @Override
    public List<GoodsReview> selectGoodsReviewList(GoodsReview goodsReview)
    {
        return goodsReviewMapper.selectGoodsReviewList(goodsReview);
    }

    /**
     * 新增评论
     * 
     * @param goodsReview 评论
     * @return 结果
     */
    @Override
    public int insertGoodsReview(GoodsReview goodsReview)
    {
        goodsReview.setCreateTime(DateUtils.getNowDate());
        return goodsReviewMapper.insertGoodsReview(goodsReview);
    }

    /**
     * 修改评论
     * 
     * @param goodsReview 评论
     * @return 结果
     */
    @Override
    public int updateGoodsReview(GoodsReview goodsReview)
    {
        goodsReview.setUpdateTime(DateUtils.getNowDate());
        return goodsReviewMapper.updateGoodsReview(goodsReview);
    }

    /**
     * 批量删除评论
     * 
     * @param ids 需要删除的评论主键
     * @return 结果
     */
    @Override
    public int deleteGoodsReviewByIds(Long[] ids)
    {
        return goodsReviewMapper.deleteGoodsReviewByIds(ids);
    }

    /**
     * 删除评论信息
     * 
     * @param id 评论主键
     * @return 结果
     */
    @Override
    public int deleteGoodsReviewById(Long id)
    {
        return goodsReviewMapper.deleteGoodsReviewById(id);
    }
}

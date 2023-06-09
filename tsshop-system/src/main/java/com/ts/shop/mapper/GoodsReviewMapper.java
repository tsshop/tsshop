package com.ts.shop.mapper;

import java.util.List;
import com.ts.shop.domain.GoodsReview;

/**
 * 评论Mapper接口
 * 
 * @author xu
 * @date 2023-02-24
 */
public interface GoodsReviewMapper 
{
    /**
     * 查询评论
     * 
     * @param id 评论主键
     * @return 评论
     */
    public GoodsReview selectGoodsReviewById(Long id);

    /**
     * 查询评论列表
     * 
     * @param goodsReview 评论
     * @return 评论集合
     */
    public List<GoodsReview> selectGoodsReviewList(GoodsReview goodsReview);

    /**
     * 新增评论
     * 
     * @param goodsReview 评论
     * @return 结果
     */
    public int insertGoodsReview(GoodsReview goodsReview);

    /**
     * 修改评论
     * 
     * @param goodsReview 评论
     * @return 结果
     */
    public int updateGoodsReview(GoodsReview goodsReview);

    /**
     * 删除评论
     * 
     * @param id 评论主键
     * @return 结果
     */
    public int deleteGoodsReviewById(Long id);

    /**
     * 批量删除评论
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsReviewByIds(Long[] ids);
}

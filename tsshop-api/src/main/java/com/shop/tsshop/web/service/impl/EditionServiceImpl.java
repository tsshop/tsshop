package com.shop.tsshop.web.service.impl;

import com.shop.tsshop.web.model.domain.Edition;
import com.shop.tsshop.web.mapper.EditionMapper;
import com.shop.tsshop.web.model.vo.EditionVo;
import com.shop.tsshop.web.service.EditionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 版本表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
@Service
public class EditionServiceImpl extends ServiceImpl<EditionMapper, Edition> implements EditionService {

    @Override
    public Object getEdition(Integer type) {
        EditionVo edition=baseMapper.getEdition(type);
        return edition;
    }
}

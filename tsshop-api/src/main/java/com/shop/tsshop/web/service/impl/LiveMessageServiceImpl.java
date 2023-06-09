package com.shop.tsshop.web.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.mapper.LiveMessageMapper;
import com.shop.tsshop.web.model.domain.LiveMessage;
import com.shop.tsshop.web.service.LiveMessageService;
/**
 * @ClassName LiveMessageServiceImpl
 * @Author TS SHOP
 * @create 2023/5/23
 */

@Service
public class LiveMessageServiceImpl extends ServiceImpl<LiveMessageMapper, LiveMessage> implements LiveMessageService{

}

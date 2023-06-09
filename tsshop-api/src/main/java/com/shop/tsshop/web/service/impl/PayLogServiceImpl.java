package com.shop.tsshop.web.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.model.domain.PayLog;
import com.shop.tsshop.web.mapper.PayLogMapper;
import com.shop.tsshop.web.service.PayLogService;
/**
 * @ClassName PayLogServiceImpl
 * @Author TS SHOP
 * @create 2023/5/24
 */

@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService{

}

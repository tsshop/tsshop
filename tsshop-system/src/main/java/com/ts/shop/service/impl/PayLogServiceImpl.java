package com.ts.shop.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.shop.domain.PayLog;
import com.ts.shop.mapper.PayLogMapper;
import com.ts.shop.service.PayLogService;
/**
 * @ClassName PayLogServiceImpl
 * @Author TS SHOP
 * @create 2023/5/24
 */

@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService{

}

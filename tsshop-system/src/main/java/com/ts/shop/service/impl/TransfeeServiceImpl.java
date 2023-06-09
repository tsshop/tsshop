package com.ts.shop.service.impl;

import com.ts.shop.mapper.TransfeeMapper;
import com.ts.shop.service.TransfeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lgh on 2018/11/16.
 */
@Service
public class TransfeeServiceImpl  implements TransfeeService {

    @Autowired
    private TransfeeMapper transfeeMapper;

}

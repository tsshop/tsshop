package com.ts.shop.service.impl;

import com.ts.shop.mapper.TranscityMapper;
import com.ts.shop.service.TranscityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lgh on 2018/11/16.
 */
@Service
public class TranscityServiceImpl  implements TranscityService {

    @Autowired
    private TranscityMapper transcityMapper;

}

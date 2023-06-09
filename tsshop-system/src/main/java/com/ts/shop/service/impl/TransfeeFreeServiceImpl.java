package com.ts.shop.service.impl;

import com.ts.shop.mapper.TransfeeFreeMapper;
import com.ts.shop.service.TransfeeFreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lgh on 2018/12/20.
 */
@Service
public class TransfeeFreeServiceImpl implements TransfeeFreeService {

    @Autowired
    private TransfeeFreeMapper transfeeFreeMapper;

}

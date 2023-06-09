package com.ts.shop.service.impl;


import com.ts.shop.mapper.TranscityFreeMapper;
import com.ts.shop.service.TranscityFreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lgh on 2018/12/20.
 */
@Service
public class TranscityFreeServiceImpl implements TranscityFreeService {

    @Autowired
    private TranscityFreeMapper transcityFreeMapper;

}

package com.shop.tsshop.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.Transport;

/**
 * @author lgh on 2018/11/16.
 */
public interface TransportService extends IService<Transport> {

    /**
     * 获取运费末班
     * @param transportId           id
     * @return                      模版
     */
    Transport getTransportAndAllItems(Long transportId);


}

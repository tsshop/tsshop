package com.shop.tsshop.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.mapper.TransportMapper;
import com.shop.tsshop.web.model.domain.TransfeeFree;
import com.shop.tsshop.web.model.domain.Transport;
import com.shop.tsshop.web.service.TransportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.List;

/**
 * @author lgh on 2018/11/16.
 */
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TransportServiceImpl extends ServiceImpl<TransportMapper, Transport> implements TransportService {

    private final TransportMapper transportMapper;


    @Override
    public Transport getTransportAndAllItems(Long transportId) {
        Transport transport = transportMapper.getTransportAndTransfeeAndTranscity(transportId);
        if (transport == null) {
            return null;
        }
        List<TransfeeFree> transfeeFrees = transportMapper.getTransfeeFreeAndTranscityFreeByTransportId(transportId);
        transport.setTransfeeFrees(transfeeFrees);
        return transport;
    }

}

package com.ts.shop.service;

import com.ts.shop.domain.Transport;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;

/**
 * @author lgh on 2018/11/16.
 */
public interface TransportService  {

    void insertTransportAndTransfee(Transport transport);

    void updateTransportAndTransfee(Transport transport);

    void deleteTransportAndTransfeeAndTranscity(Long[] ids);

    Transport getTransportAndAllItems(Long transportId);

    @CacheEvict(cacheNames = "TransportAndAllItems", key = "#transportId")
    default void removeTransportAndAllItemsCache(Long transportId) {
    }

    public List<Transport> selectTransportList(Transport transport);
}

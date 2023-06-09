package com.ts.shop.mapper;


import com.ts.shop.domain.TransfeeFree;
import com.ts.shop.domain.Transport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TransportMapper {

    Transport getTransportAndTransfeeAndTranscity(@Param("id") Long id);

    void deleteTransports(@Param("ids") Long[] ids);

    List<TransfeeFree> getTransfeeFreeAndTranscityFreeByTransportId(@Param("transportId") Long transportId);

    /**
     * 新增运费模板
     *
     * @param transport 运费模板
     * @return 结果
     */
    public int insert(Transport transport);

    /**
     * 修改运费模板
     *
     * @param transport 运费模板
     * @return 结果
     */
    public int updateTransport(Transport transport);

    List<Transport> selectTransportList(Transport transport);
}
package com.shop.tsshop.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.domain.TransfeeFree;
import com.shop.tsshop.web.model.domain.Transport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TransportMapper extends BaseMapper<Transport> {

    Transport getTransportAndTransfeeAndTranscity(@Param("id") Long id);

    List<TransfeeFree> getTransfeeFreeAndTranscityFreeByTransportId(@Param("transportId") Long transportId);

}
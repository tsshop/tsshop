package com.ts.shop.mapper;


import com.ts.shop.domain.Transcity;
import com.ts.shop.domain.TranscityFree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TranscityMapper {

	void insertTranscities(@Param("transcities") List<Transcity> transcities);

	void insertTranscityFrees(@Param("transcityFrees") List<TranscityFree> transcityFrees);

	List<Transcity> listTranscityAndArea(@Param("transfeeId") Long transfeeId);

	void deleteBatchByTransfeeIds(@Param("transfeeIds") List<Long> transfeeIds);

	void deleteBatchByTransfeeFreeIds(@Param("transfeeFreeIds") List<Long> transfeeFreeIds);

}
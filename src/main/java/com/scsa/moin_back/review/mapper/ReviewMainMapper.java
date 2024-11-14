package com.scsa.moin_back.review.mapper;

import com.scsa.moin_back.review.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface ReviewMainMapper {
    List<ReviewDTO> getReviewList(Map<String,Object> searchParam);

    int getReviewListCnt(Map<String,Object> searchParam);
}

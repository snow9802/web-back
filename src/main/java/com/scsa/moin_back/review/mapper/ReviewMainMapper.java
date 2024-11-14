package com.scsa.moin_back.review.mapper;

import com.scsa.moin_back.review.dto.ReviewDTO;
import com.scsa.moin_back.review.vo.ReviewVO;

import java.util.List;
import java.util.Map;

public interface ReviewMainMapper {
    List<ReviewDTO> getReviewList(Map<String,Object> searchParam);

    int getReviewListCnt(Map<String,Object> searchParam);
}

package com.scsa.moin_back.review.service;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.review.dto.ReviewDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;

import java.util.Map;

public interface ReviewService {
    PageDTO<ReviewDTO> getReviewList(Map<String, Object> map, int currentPage, int pageSize);

    ReviewDetailDTO getReviewDetail(int reviewId);
}

package com.scsa.moin_back.review.mapper;

import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewDetailMapper {
        ReviewDetailDTO getReviewDetail(int reviewId);

}

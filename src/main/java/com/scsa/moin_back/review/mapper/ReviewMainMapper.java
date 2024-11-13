package com.scsa.moin_back.review.mapper;

import com.scsa.moin_back.review.vo.ReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
public interface ReviewMainMapper {
    List<ReviewVO> getReviewList();
}

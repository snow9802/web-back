package com.scsa.moin_back.review.service;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.review.dto.ReviewDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.mapper.ReviewDetailMapper;
import com.scsa.moin_back.review.mapper.ReviewMainMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    final private ReviewMainMapper reviewMainMapper;
    private final ReviewDetailMapper reviewDetailMapper;

    public PageDTO<ReviewDTO> getReviewList(Map<String, Object> map, int currentPage, int pageSize) {

        int startRow = (currentPage - 1) * pageSize + 1;
        int endRow = currentPage * pageSize;
        map.put("startRow", startRow);
        map.put("endRow", endRow);

        List<ReviewDTO> list = reviewMainMapper.getReviewList(map);
        int totalCnt = reviewMainMapper.getReviewListCnt(map);

        int pageGroupSize = 5; //페이지그룹의 페이지 수 ex)현재페이지 1인경우 1 2 3
        return new PageDTO<ReviewDTO>(pageSize, pageGroupSize, currentPage, totalCnt, list);
    }

    public ReviewDetailDTO getReviewDetail(int reviewId){
        return reviewDetailMapper.getReviewDetail(reviewId);

    }


}

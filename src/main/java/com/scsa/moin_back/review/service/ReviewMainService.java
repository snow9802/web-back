package com.scsa.moin_back.review.service;

import com.scsa.moin_back.review.mapper.ReviewMainMapper;
import com.scsa.moin_back.review.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewMainService {
    private Logger logger = LoggerFactory.getLogger(ReviewMainService.class);

    final private ReviewMainMapper reviewMainMapper;

    public List<ReviewVO> test() {
        return reviewMainMapper.getReviewList();
    }


}

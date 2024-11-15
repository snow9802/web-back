package com.scsa.moin_back.review.service;

import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.mapper.ReviewDetailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewDetailServiceImpl implements ReviewDetailService {

    private final ReviewDetailMapper reviewDetailMapper;

    @Override
    public ReviewDetailDTO getReviewDetail(int reviewId) {
        return reviewDetailMapper.getReviewDetail(reviewId);

    }

    @Override
    public void removeReview(int reviewId) {

    }

    @Override
    public void registReviewComment(int reviewId) {

    }

    @Override
    public void modifyReviewComment(int reviewCommentId) {

    }

    @Override
    public void removeReviewComment(int reviewCommentId) {

    }

    @Override
    public void registReviewRecomment(int reviewCommentId) {

    }

    @Override
    public void modifyReviewRecomment(int reviewRecommentId) {

    }

    @Override
    public void removeReviewRecomment(int reviewRecommentId) {

    }


}

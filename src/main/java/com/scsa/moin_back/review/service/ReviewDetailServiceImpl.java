package com.scsa.moin_back.review.service;

import com.scsa.moin_back.review.dto.ReviewCommentDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.dto.ReviewRecommentDTO;
import com.scsa.moin_back.review.mapper.ReviewDetailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewDetailServiceImpl implements ReviewDetailService {

    private final ReviewDetailMapper reviewDetailMapper;

    @Override
    public ReviewDetailDTO getReviewDetail(int reviewId) {
        /*리뷰 상세페이지 조회*/
        //1.리뷰 상세 내용 조회
        ReviewDetailDTO reviewDetailDTO = reviewDetailMapper.getReviewDetail(reviewId);
        //2.리뷰 댓글 내용 조회
        reviewDetailDTO.setReviewCommentList(reviewDetailMapper.getReviewComment(reviewId));
        //3.리뷰 모임 정보 조회
        reviewDetailDTO.setReviewGroup(reviewDetailMapper.getReviewGroup(reviewDetailDTO.getReviewGroupId()));
        //4.리뷰 모임 추천 조회
        reviewDetailDTO.setReviewRecGroup(reviewDetailMapper.getReviewRecGroup(reviewDetailDTO.getReviewGroupId()));

        return reviewDetailDTO;
    }

    @Override
    @Transactional
    public void removeReview(int reviewId) {
        reviewDetailMapper.deleteReviewRecmtBfReview(reviewId);
        reviewDetailMapper.deleteReviewCmtBfReview(reviewId);
        reviewDetailMapper.deleteReviewImgsBfReview(reviewId);
        reviewDetailMapper.deleteReview(reviewId);
    }

    @Override
    @Transactional
    public void registReviewComment(ReviewCommentDTO reviewCommentDTO) {
        reviewDetailMapper.insertReviewComment(reviewCommentDTO);

    }

    @Override
    @Transactional
    public void modifyReviewComment(ReviewCommentDTO reviewCommentDTO) {
        reviewDetailMapper.updateReviewComment(reviewCommentDTO);

    }

    @Override
    @Transactional
    public void removeReviewComment(int reviewCommentId) {
        reviewDetailMapper.deleteReviewComment(reviewCommentId);

    }

    @Override
    @Transactional
    public void registReviewRecomment(ReviewRecommentDTO reviewRecommentDTO) {
        reviewDetailMapper.insertReviewRecomment(reviewRecommentDTO);

    }

    @Override
    @Transactional
    public void modifyReviewRecomment(ReviewRecommentDTO reviewRecommentDTO) {
        reviewDetailMapper.updateReviewRecomment(reviewRecommentDTO);
    }

    @Override
    @Transactional
    public void removeReviewRecomment(int reviewRecommentId) {
        reviewDetailMapper.deleteReviewRecomment(reviewRecommentId);
    }


}

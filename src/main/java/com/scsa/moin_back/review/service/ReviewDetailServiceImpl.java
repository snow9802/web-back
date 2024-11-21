package com.scsa.moin_back.review.service;

import com.scsa.moin_back.review.dto.ReviewCommentDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.dto.ReviewImgDTO;
import com.scsa.moin_back.review.dto.ReviewRecommentDTO;
import com.scsa.moin_back.review.exception.AddReviewException;
import com.scsa.moin_back.review.exception.FindReviewException;
import com.scsa.moin_back.review.exception.ModifyReviewException;
import com.scsa.moin_back.review.exception.RemoveReviewException;
import com.scsa.moin_back.review.mapper.ReviewDetailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewDetailServiceImpl implements ReviewDetailService {

    private final ReviewDetailMapper reviewDetailMapper;

    @Override
    public ReviewDetailDTO getReviewDetail(int reviewId) throws FindReviewException {
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
    @Transactional(rollbackFor = RemoveReviewException.class)
    public void removeReview(int reviewId) {
        reviewDetailMapper.deleteReviewRecmtBfReview(reviewId);
        reviewDetailMapper.deleteReviewCmtBfReview(reviewId);
        reviewDetailMapper.deleteReviewImgsBfReview(reviewId);
        reviewDetailMapper.deleteReview(reviewId);
    }

    @Override
    @Transactional(rollbackFor = AddReviewException.class)
    public void registReviewComment(ReviewCommentDTO reviewCommentDTO) {
        reviewDetailMapper.insertReviewComment(reviewCommentDTO);

    }

    @Override
    @Transactional(rollbackFor = ModifyReviewException.class)
    public void modifyReviewComment(ReviewCommentDTO reviewCommentDTO) {
        reviewDetailMapper.updateReviewComment(reviewCommentDTO);

    }

    @Override
    @Transactional(rollbackFor = RemoveReviewException.class)
    public void removeReviewComment(int reviewCommentId) {
        reviewDetailMapper.deleteReviewRecommentBfCmt(reviewCommentId);
        reviewDetailMapper.deleteReviewComment(reviewCommentId);

    }

    @Override
    @Transactional(rollbackFor = AddReviewException.class)
    public void registReviewRecomment(ReviewRecommentDTO reviewRecommentDTO) {
        reviewDetailMapper.insertReviewRecomment(reviewRecommentDTO);

    }

    @Override
    @Transactional(rollbackFor = ModifyReviewException.class)
    public void modifyReviewRecomment(ReviewRecommentDTO reviewRecommentDTO) {
        reviewDetailMapper.updateReviewRecomment(reviewRecommentDTO);
    }

    @Override
    @Transactional(rollbackFor = RemoveReviewException.class)
    public void removeReviewRecomment(int reviewRecommentId)
    {
        reviewDetailMapper.deleteReviewRecomment(reviewRecommentId);
    }


}

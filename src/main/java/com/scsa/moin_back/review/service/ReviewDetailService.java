package com.scsa.moin_back.review.service;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.review.dto.ReviewDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.exception.AddReviewException;

import java.util.Map;

public interface ReviewDetailService {

    /**
     * 리뷰 상세 조회
     * @param reviewId
     * @return
     */
    ReviewDetailDTO getReviewDetail(int reviewId);

    /**
     * 리뷰 삭제
     * @param reviewId
     */
    void removeReview(int reviewId);

    /**
     * 리뷰 댓글 등록
     * @param reviewId
     */
    void registReviewComment(int reviewId);

    /**
     * 리뷰 댓글 수정
     * @param reviewCommentId
     */
    void modifyReviewComment(int reviewCommentId);

    /**
     * 리뷰 댓글 삭제
     * @param reviewCommentId
     */
    void removeReviewComment(int reviewCommentId);

    /**
     * 리듀
     * @param reviewCommentId
     */
    void registReviewRecomment(int reviewCommentId);

    void modifyReviewRecomment(int reviewRecommentId);

    void removeReviewRecomment(int reviewRecommentId);
}

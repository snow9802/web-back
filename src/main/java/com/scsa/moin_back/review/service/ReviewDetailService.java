package com.scsa.moin_back.review.service;

import com.scsa.moin_back.review.dto.ReviewCommentDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.dto.ReviewRecommentDTO;
import com.scsa.moin_back.review.exception.FindReviewException;
import com.scsa.moin_back.review.exception.RemoveReviewException;

public interface ReviewDetailService {

    /**
     * 리뷰 상세 조회
     * @param reviewId
     * @return
     */
    ReviewDetailDTO getReviewDetail(int reviewId) throws FindReviewException;

    /**
     * 리뷰 삭제
     * @param reviewId
     */
    void removeReview(int reviewId);

    /**
     * 리뷰 댓글 등록
     * @param reviewCommentDTO
     */
    void registReviewComment(ReviewCommentDTO reviewCommentDTO);

    /**
     * 리뷰 댓글 수정
     * @param reviewCommentDTO
     */
    void modifyReviewComment(ReviewCommentDTO reviewCommentDTO);

    /**
     * 리뷰 댓글 삭제
     * @param reviewCommentId
     */
    void removeReviewComment(int reviewCommentId);

    /**
     * 리뷰 대댓글 등록
     * @param reviewRecommentDTO
     */
    void registReviewRecomment(ReviewRecommentDTO reviewRecommentDTO);

    /**
     * 리뷰 대댓글 수정
     * @param reviewRecommentDTO
     */
    void modifyReviewRecomment(ReviewRecommentDTO reviewRecommentDTO);

    /**
     * 라뷰 대댓글 삭제
     * @param reviewRecommentId
     */
    void removeReviewRecomment(int reviewRecommentId) throws RemoveReviewException;
}

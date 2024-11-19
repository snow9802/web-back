package com.scsa.moin_back.review.mapper;

import com.scsa.moin_back.review.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewDetailMapper {
        ReviewDetailDTO getReviewDetail(int reviewId);
        ReviewGroupDTO getReviewGroup(int groupId);
        ReviewGroupDTO getReviewRecGroup(int categoryId);
        List<ReviewCommentDTO> getReviewComment(int reviewId);
        List<ReviewImgDTO> getReviewImages(int reviewId);
        void deleteReviewRecmtBfReview(int reviewId);
        void deleteReviewImgsBfReview(int reviewId);
        void deleteReviewCmtBfReview(int reviewId);
        void deleteReview(int reviewId);


        void insertReviewComment(ReviewCommentDTO reviewCommentDTO);
        void insertReviewRecomment(ReviewRecommentDTO reviewRecommentDTO);
        void updateReviewComment(ReviewCommentDTO reviewCommentDTO);
        void updateReviewRecomment(ReviewRecommentDTO reviewRecommentDTO);
        void deleteReviewRecommentBfCmt(int reviewCommentId);
        void deleteReviewComment(int reviewId);
        void deleteReviewRecomment(int recommentId);

}

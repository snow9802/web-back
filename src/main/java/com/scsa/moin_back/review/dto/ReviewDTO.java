package com.scsa.moin_back.review.dto;

import com.scsa.moin_back.review.vo.ReviewCommentVO;
import com.scsa.moin_back.review.vo.ReviewImgVO;
import com.scsa.moin_back.review.vo.ReviewRecommentVO;
import lombok.*;

import java.util.Date;
import java.util.List;

@ToString
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ReviewDTO {
    private int reviewId;
    private int reviewGroupId;
    private int participationId;
    private String reviewTitle;
    private String reviewContent;
    private Date createdAt;

    private List<ReviewCommentVO> reviewCommentList;
    private List<ReviewRecommentVO> reviewRecommentList;
    private List<ReviewImgVO> reviewImgList;


}

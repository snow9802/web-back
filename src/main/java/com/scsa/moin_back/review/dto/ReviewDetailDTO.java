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
public class ReviewDetailDTO {
    private int reviewId;
    private int reviewGroupId;
    private int reviewCommentCnt;
    private int categoryId;
    private int groupId;
    private String reviewWriter;
    private String reviewTitle;
    private String reviewContent;
    private Date createdAt;
    private ReviewGroupDTO reviewGroup;
    private ReviewGroupDTO reviewRecGroup;
    private List<ReviewImgDTO> reviewImgList;
    private List<ReviewCommentDTO> reviewCommentList;
}

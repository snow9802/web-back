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
    private int reviewCommentCnt;
    private int categoryId;
    private int groupId;
    private String reviewWriter;
    private String id;
    private String reviewTitle;
    private String reviewContent;
    private String categoryName;
    private String groupName;
    private String city;
    private String district;
    private String reviewImgUrl;
    private Date createdAt;


    private List<ReviewCommentVO> reviewCommentList;
    private List<ReviewRecommentVO> reviewRecommentList;
    private List<ReviewImgVO> reviewImgList;
}

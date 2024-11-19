package com.scsa.moin_back.review.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewCommentDTO {
    private int reviewCommentId;
    private int reviewId;
    private String commentContent;
    private String rcWriter;
    private String rcWriterId;
    private String rcProfileUrl;
    private Date rcCreatedAt;
    private List<ReviewRecommentDTO> reviewRecommentList;
}

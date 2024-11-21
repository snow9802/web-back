package com.scsa.moin_back.review.vo;

import lombok.*;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewCommentVO {
    private int reviewCommentId;
    private int reviewId;
    private String id;
    private String commentContent;
    private Date createdAt;
}

package com.scsa.moin_back.review.vo;

import lombok.*;

import java.util.Date;

@ToString
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ReviewVO {
    private int reviewId;
    private int reviewGroupId;
    private int participationId;
    private String reviewTitle;
    private String reviewContent;
    private Date createdAt;

}

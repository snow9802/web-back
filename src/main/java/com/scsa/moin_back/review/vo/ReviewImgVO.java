package com.scsa.moin_back.review.vo;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewImgVO {
    private int reviewImgId;
    private int reviewId;
    private String reviewImgUrl;
}

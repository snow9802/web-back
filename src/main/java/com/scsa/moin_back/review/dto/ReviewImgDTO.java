package com.scsa.moin_back.review.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewImgDTO {
    private int reviewImgId;
    private int reviewId;
    private String reviewImgUrl;
}

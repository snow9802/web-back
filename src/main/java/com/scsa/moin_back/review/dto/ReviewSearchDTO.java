package com.scsa.moin_back.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class ReviewSearchDTO {
    String searchParam = "";
    String city = "all";
    String district = "all";
}
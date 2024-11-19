package com.scsa.moin_back.review.dto;

import lombok.*;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewRecommentDTO {
    private int reviewRecommentId;
    private int reviewCommentId;
    private String id;
    private String recommentContent;
    private String rrcWriter;
    private String rrcWriterId;
    private String rrcProfileUrl;
    private Date rrcCreatedAt;
}

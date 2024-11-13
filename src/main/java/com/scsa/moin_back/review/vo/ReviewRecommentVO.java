package com.scsa.moin_back.review.vo;

import lombok.*;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewRecommentVO {
    private int reviewRecommentId;
    private int reviewCommentId;
    private int id;
    private String recommentContent;
    private Date createdAt;
}

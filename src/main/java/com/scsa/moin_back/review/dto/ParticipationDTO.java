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
public class ParticipationDTO {
    private String parcitipationId;
    private String id;
    private String groupId;
    private String nickname;
    private String profileUrl;
}

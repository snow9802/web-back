package com.scsa.moin_back.review.dto;

import lombok.*;

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

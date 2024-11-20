package com.scsa.moin_back.review.dto;

import com.scsa.moin_back.group.vo.GroupVO;
import lombok.*;

import java.util.List;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class ReviewGroupDTO {
    private GroupVO groupVo;
    private int groupId;
    private String categoryName;
    private List<ParticipationDTO> participationList;
}

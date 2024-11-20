package com.scsa.moin_back.group.dto;

import com.scsa.moin_back.group.vo.GroupDetailVO;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class GroupDetailDTO {
    private GroupDetailVO groupDetailVO;
    private String isRecruit; // 현재 모임의 모집중 여부
    private String isLike; // 현재 유저가 해당 모임을 좋아하는지 여부
    private String isParticipation; // 해당 유저가 참여중인지 체크
    private String categoryName; // 해당하는 카테코리 이름
    private String groupLeaderName;
}

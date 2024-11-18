package com.scsa.moin_back.group.dto;

import com.scsa.moin_back.group.vo.GroupVO;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class GroupDTO {
    private int groupId;
    private GroupVO group;
    private String isCurUserFavorite;
    private long dDay;
    private String categoryName;
}

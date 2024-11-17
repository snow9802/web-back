package com.scsa.moin_back.group.dto;

import com.scsa.moin_back.category.vo.CategoryVO;
import com.scsa.moin_back.group.vo.GroupVO;
import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class GroupModifyDTO {
    private GroupVO groupVO;
    private List<CategoryVO> categoryVOList;
}

package com.scsa.moin_back.group.vo;

import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import com.scsa.moin_back.member.vo.MemberVO;
import lombok.*;

import java.util.List;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class GroupDetailVO {
    private GroupVO group;
    private List<MemberVO> members;
    private List<GroupCommentVO> groupCommentList;
}

package com.scsa.moin_back.groupcomment.vo;

import com.scsa.moin_back.grouprecomment.vo.GroupRecommentVO;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class GroupCommentVO {
    private int commentId;
    private int groupId;
    private String id;
    private String writerNickname;
    private Date createdAt;
    private String commentContent;
    private List<GroupRecommentVO> groupRecomentList;
}

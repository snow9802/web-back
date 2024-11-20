package com.scsa.moin_back.groupcomment.mapper;

import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupCommentMapper {

    int insertGroupComment(GroupCommentVO groupCommentVO);

    int updateGroupComment(GroupCommentVO groupComment);

    int deleteGroupComment(GroupCommentVO groupComment);

    int updateGroupCommentCntPlus(GroupCommentVO groupComment);

    int updateGroupCommentCntMinus(GroupCommentVO groupComment);
}

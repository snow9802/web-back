package com.scsa.moin_back.groupcomment.mapper;

import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupCommentMapper {

    void insertGroupComment(GroupCommentVO groupCommentVO);

    int updateGroupComment(GroupCommentVO groupComment);

    int deleteGroupComment(GroupCommentVO groupComment);
}

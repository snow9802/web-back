package com.scsa.moin_back.grouprecomment.mapper;

import com.scsa.moin_back.grouprecomment.vo.GroupRecommentVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupRecommentMapper {
    void insertGroupRecomment(GroupRecommentVO groupRecomment);

    int updateGroupRecomment(GroupRecommentVO groupRecomment);

    int deleteGroupRecomment(GroupRecommentVO groupRecomment);
}

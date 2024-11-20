package com.scsa.moin_back.group.mapper;

import com.scsa.moin_back.group.vo.GroupVO;
import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import com.scsa.moin_back.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupDetailMapper {
    /**
     * 모임 상세 조회
     * @param groupId
     * @return
     */
    GroupVO getGroupDetail(int groupId);

    /**
     * 모임 참여자 조회
     * @param integer
     * @return
     */
    List<MemberVO> getGroupDetailMember(Integer integer);

    /**
     * 모임 댓글 조회
     * @param integer
     * @return
     */
    List<GroupCommentVO> getGroupDetailComments(Integer integer);

    /**
     * 현재 유저가 해당 게시글을 좋아하는지 여부 조회
     * @param groupId
     * @param id
     * @return
     */
    MemberVO checkMemberLike(@Param("groupId") int groupId, @Param("id") String id);

    /**
     * 현재 카테고리 ID에 해당하는 카테고리 이름 찾기
     * @param categoryId
     * @return
     */
    String getCategoryNameById(@Param("categoryId") int categoryId);

    /**
     * 현재 userId에 해당하는 nickname 찾기
     * @param groupLeaderId
     * @return
     */
    String getGroupLeaderName(String groupLeaderId);
}

package com.scsa.moin_back.group.mapper;

import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.group.dto.GroupDetailDTO;
import com.scsa.moin_back.group.vo.GroupVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface GroupMainMapper {
    /**
     * 모집중인 모임 조회
     * @param paramMap
     * @return
     */
    List<GroupDTO> getGroupsActive(Map<String, Object> paramMap);

    /**
     * 모집마감된 모임 조회
     * @param paramMap
     * @return
     */
    List<GroupDTO> getGroupsNotActive(Map<String, Object> paramMap);

    /**
     * 모집 중인 모임의 총 개수 조회
     * @param paramMap
     * @return
     */
    int getGroupsActiveCnt(Map<String, Object> paramMap);


    /**
     * 모집마감된 모임의 총 개수 조회
     * @param paramMap
     * @return
     */
    int getGroupsNotActiveCnt(Map<String, Object> paramMap);

    /**
     * 현재 id로 그룹 조회
     * @param groupId
     * @return
     */
    GroupVO searchGroupById(String userId, int groupId);

    /**
     * 모임 삭제 (SOFT 업데이트)
     * @param groupId
     */
    void modifyGroupRemove(int groupId);

    /**
     * 모임 등록
     * @param group
     */
    int insertGroup(GroupVO group);

    /**
     * 모임 정보 수정
     * @param group
     */
    void updateGroup(GroupVO group);

    /**
     * 모임 테이블의 좋아요 행 값 + 1
     * @param paramMap
     * @return
     */
    int updateGroupLike(HashMap<String, Object> paramMap);

    /**
     * 좋아요 테이블에 행 추가
     * @param paramMap
     * @return
     */
    int insertLike(HashMap<String, Object> paramMap);

    /**
     * 좋아요 테이블에 행 삭제
     * @param paramMap
     * @return
     */
    int deleteLike(HashMap<String, Object> paramMap);

    /**
     * 모임 테이블 좋아요 행 값 - 1
     * @param paramMap
     * @return
     */
    int updateGroupLikeMinus(HashMap<String, Object> paramMap);

    /**
     * 내가 만든 모임 목록 조회
     * @param paramMap
     * @return
     */
    List<GroupDTO> getMyGroups(Map<String, Object> paramMap);

    /**
     * 내가 만든 모임 개수 조회
     * @param paramMap
     * @return
     */
    int getMyGroupsCnt(Map<String, Object> paramMap);

    /**
     * 내가 참여중인 모임 목록 조회
     * @param paramMap
     * @return
     */
    List<GroupDTO> getMyParticipationGroups(Map<String, Object> paramMap);

    /**
     * 내가 참여중인 모임 개수 조회
     * @param paramMap
     * @return
     */
    int getMyParticipationGroupsCnt(Map<String, Object> paramMap);

    /**
     * 내가 참여중인 모임 중 모임 기간이 지난 모임 조회
     * @param paramMap
     * @return
     */
    List<GroupDTO> getMyParticipationPastGroups(Map<String, Object> paramMap);

    /**
     * 내가 참여중인 모임 중 모임 기간이 지난 모임 개수 조회
     * @param paramMap
     * @return
     */
    int getMyParticipationPastGroupsCnt(Map<String, Object> paramMap);

    /**
     * 내가 좋아요 한 모임 조회
     * @param paramMap
     * @return
     */
    List<GroupDTO> getMyFavoriteGroups(Map<String, Object> paramMap);

    /**
     * 내가 좋아요 한 모임 개수 조회
     * @param paramMap
     * @return
     */
    int getMyFavoriteGroupsCnt(Map<String, Object> paramMap);

    GroupVO searchGroupByIdNow(int groupId);
}

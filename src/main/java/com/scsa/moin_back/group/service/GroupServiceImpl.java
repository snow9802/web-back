package com.scsa.moin_back.group.service;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.group.dto.GroupDetailDTO;
import com.scsa.moin_back.group.mapper.GroupDetailMapper;
import com.scsa.moin_back.group.mapper.GroupMainMapper;
import com.scsa.moin_back.group.vo.GroupDetailVO;
import com.scsa.moin_back.group.vo.GroupVO;
import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import com.scsa.moin_back.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements IGroupService {

    private final GroupMainMapper groupMainMapper;
    
    @Override
    public PageDTO<GroupDTO> getGroups(String userId, Optional<Integer> currentPage, Optional<Integer> pageSize, Optional<String> category, String searchParam, String city, String district, String isActive) {

        /* pagination에 필요한 정보 추가 */
        int pageGroupSize = 5; // 화면에 보여줄 페이지 그룹의 개수 (1, 2, 3, 4, 5)
        int curPage = currentPage.orElse(1); // 현재 페이지 번호
        int ps = pageSize.orElse(9); // 한 화면에 보여줄 객체의 수 9
        int startRow = (curPage - 1) * ps + 1;
        int endRow = curPage * ps;

        /* SQL문 parameter로 넘길 map 형성 */
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("searchParam", searchParam);
        paramMap.put("city", city);
        paramMap.put("district", district);
        paramMap.put("category", category.orElse("all"));
        paramMap.put("startRow", startRow);
        paramMap.put("endRow", endRow);

        /* 모집 여부에 따른 분기 처리 */
        if ("Y".equals(isActive)){
            List<GroupDTO> groupDTOList = groupMainMapper.getGroupsActive(paramMap);
            checkFavDate(groupDTOList); // GroupDTO 세팅

            int totalCnt = groupMainMapper.getGroupsActiveCnt(paramMap);
            return new PageDTO<>(ps, pageGroupSize, curPage, totalCnt, groupDTOList);
        } else {
            List<GroupDTO> groupDTOList = groupMainMapper.getGroupsNotActive(paramMap);
            checkFavDate(groupDTOList); // GroupDTO 세팅

            int totalCnt = groupMainMapper.getGroupsNotActiveCnt(paramMap);
            return new PageDTO<>(ps, pageGroupSize, curPage, totalCnt, groupDTOList);
        }
    }

    @Override
    public ResponseEntity<Object> removeGroup(HashMap<String, Object> paramMap) {
        int groupId = Integer.parseInt(paramMap.get("groupId").toString());

        try{
            if (groupMainMapper.searchGroupById(groupId) != null){
                groupMainMapper.modifyGroupRemove(groupId);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * 모임 등록
     * @param group
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<Object> registGroup(GroupVO group) {

        /* group에 대한 default value 체크 */
        if (group.getGroupImg() == null){
            group.setGroupImg("default");
        }
        group.setParticipationCount(1); // 방장 기본 수행

        try {
            /* 모임 테이블 insert */
            System.out.println(group);
            groupMainMapper.insertGroup(group); // selectkey를 활용해 autoIncrement된 groupId 얻어오기

            /* 참여 테이블 insert (방장 참여) */
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("groupId", group.getGroupId());
            paramMap.put("id", group.getGroupLeaderId());

            groupMainMapper.insertParticipation(paramMap);

            /* 결과 리턴 */
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * 각 모임을 순회하며 isCurUserFavorite, dday 세팅
     * @param groupDTOList
     */
    private void checkFavDate(List<GroupDTO> groupDTOList) {
        for (GroupDTO groupDTO : groupDTOList) {
            if (groupDTO.getIsCurUserFavorite() != null){
                groupDTO.setIsCurUserFavorite("Y");
            } else {
                groupDTO.setIsCurUserFavorite("N");
            }

            LocalDate curDate = LocalDate.now();
            LocalDate targetDate = groupDTO.getGroup().getGroupDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long dayBetween = ChronoUnit.DAYS.between(curDate, targetDate);
            groupDTO.setDDay(dayBetween);
        }
    }
}

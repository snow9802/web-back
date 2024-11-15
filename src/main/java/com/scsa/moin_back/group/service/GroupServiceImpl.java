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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements IGroupService {

    private final GroupMainMapper groupMainMapper;
    private final GroupDetailMapper groupDetailMapper;

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

    @Override
    public GroupDetailDTO getGroupDetail(Optional<Integer> groupId, String id) {

        if (groupId.isEmpty()) {
            throw new NoSuchElementException();
        }

        List<MemberVO> groupMember = groupDetailMapper.getGroupDetailMember(groupId.get());
        List<GroupCommentVO> groupCommentVOList = groupDetailMapper.getGroupDetailComments(groupId.get());
        GroupVO group = groupDetailMapper.getGroupDetail(groupId.get());

        /* groupDetailDTO 세팅 */
        GroupDetailDTO groupDetailDTO = new GroupDetailDTO();
        GroupDetailVO groupDetailVO = new GroupDetailVO();

        groupDetailVO.setGroup(group);
        groupDetailVO.setMembers(groupMember);
        groupDetailVO.setGroupCommentList(groupCommentVOList);

        groupDetailDTO.setGroupDetailVO(groupDetailVO);

        /* 조건 처리를 통한 groupDetailDTO 추가 세팅 */
        GroupVO curGroup = groupDetailVO.getGroup();

        String isRecruit = "Y";
        String isLike = "N";
        String isParticipation = "N";
        if ("Y".equals(curGroup.getManualCloseYn()) ||
                curGroup.getGroupLimit() <= curGroup.getParticipationCount() ||
                new Date().compareTo(curGroup.getCloseDate()) > 0){ // 현재 시점이 마감 시점보다 이후
            isRecruit = "N";
        }

        if (groupDetailMapper.checkMemberLike(groupId.get(), id) != null) {
            isLike = "Y";
        }

        for (MemberVO member : groupMember) {
            if (member.getId().equals(id)) {
                isParticipation = "Y";
                break;
            }
        }

        groupDetailDTO.setIsRecruit(isRecruit);
        groupDetailDTO.setIsLike(isLike);
        groupDetailDTO.setIsParticipation(isParticipation);

        System.out.println(groupDetailDTO);
        return groupDetailDTO;
    }
}

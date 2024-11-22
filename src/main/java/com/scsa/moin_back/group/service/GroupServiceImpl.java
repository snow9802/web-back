package com.scsa.moin_back.group.service;

import com.scsa.moin_back.category.mapper.CategoryMapper;
import com.scsa.moin_back.category.vo.CategoryVO;
import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.common.service.FileUploader;
import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.group.dto.GroupModifyDTO;
import com.scsa.moin_back.group.mapper.GroupDetailMapper;
import com.scsa.moin_back.group.mapper.GroupMainMapper;
import com.scsa.moin_back.group.mapper.GroupParticipationMapper;
import com.scsa.moin_back.group.vo.GroupVO;
import com.scsa.moin_back.group.vo.ParticipationNum;
import com.scsa.moin_back.member.config.SecurityConfig;
import com.scsa.moin_back.member.config.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements IGroupService {

    private final GroupMainMapper groupMainMapper;
    private final CategoryMapper categoryMapper;
    private final GroupParticipationMapper groupParticipationMapper;
    private final FileUploader fileUploader;
    private final GroupDetailMapper groupDetailMapper;
    private final SecurityUtil securityUtil;

    @Override
    public PageDTO<GroupDTO> getGroups(String userId, Optional<Integer> currentPage, Optional<Integer> pageSize, Optional<String> category, String searchParam, String city, String district, String isActive) {

        /* pagination에 필요한 정보 추가 */
        int pageGroupSize = 5; // 화면에 보여줄 페이지 그룹의 개수 (1, 2, 3, 4, 5)
        int curPage = currentPage.orElse(1); // 현재 페이지 번호
        int ps = pageSize.orElse(9); // 한 화면에 보여줄 객체의 수 9
        int startRow = (curPage - 1) * ps + 1;
        int endRow = curPage * ps;

        System.out.println(currentPage);
        System.out.println(pageSize);

        /* SQL문 parameter로 넘길 map 형성 */
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("searchParam", searchParam);
        paramMap.put("city", city);
        paramMap.put("district", district);
        paramMap.put("category", category.orElse("all"));
        paramMap.put("startRow", startRow);
        paramMap.put("endRow", endRow);
        paramMap.put("id", userId);

        System.out.println(isActive);
        /* 모집 여부에 따른 분기 처리 */
        if ("Y".equals(isActive)){
            System.out.println("isActive Y");
            List<GroupDTO> groupDTOList = groupMainMapper.getGroupsActive(paramMap);

            checkFavDate(groupDTOList);
            int totalCnt = groupMainMapper.getGroupsActiveCnt(paramMap);
            return new PageDTO<>(ps, pageGroupSize, curPage, totalCnt, groupDTOList);
        } else {
            System.out.println("isActive N");
            List<GroupDTO> groupDTOList = groupMainMapper.getGroupsNotActive(paramMap);

            checkFavDate(groupDTOList);
            int totalCnt = groupMainMapper.getGroupsNotActiveCnt(paramMap);
            return new PageDTO<>(ps, pageGroupSize, curPage, totalCnt, groupDTOList);
        }
    }

    @Override
    public ResponseEntity<Object> removeGroup(HashMap<String, Object> paramMap) {
        int groupId = Integer.parseInt(paramMap.get("groupId").toString());
        String userId = securityUtil.getCurrentMemberId();

        if (userId == null || "anonymousUser".equals(userId)) {
            return ResponseEntity.status(400).build();
        }

        try{
            if (groupMainMapper.searchGroupById(userId, groupId) != null){
                groupMainMapper.modifyGroupRemove(groupId);
                return ResponseEntity.ok().build();
            } else {
                System.out.println("그룹에 대한 권한이 없습니다.");
                return ResponseEntity.status(405).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }


    @Override
    @Transactional
    public ResponseEntity<Object> registGroup(GroupVO group, MultipartFile fileImg) {
        String fileUrl = "default url";

        /* img 파일 업로드 */
        if (fileImg != null){
            try{
                fileUrl = fileUploader.uploadFile(fileImg);
            } catch (Exception e){
                return ResponseEntity.status(400).build(); // 파일 업로드 실패
            }
        }

        group.setGroupImg(fileUrl);
        group.setParticipationCount(1); // 방장 기본 참여

        try {
            /* 모임 테이블 insert */
            System.out.println(group);
            groupMainMapper.insertGroup(group); // selectkey를 활용해 autoIncrement된 groupId 얻어오기

            /* 참여 테이블 insert (방장 참여) */
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("groupId", group.getGroupId());
            paramMap.put("id", group.getGroupLeaderId());

            groupParticipationMapper.registParticipation(paramMap);

            /* 결과 리턴 */
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @Override
    public ResponseEntity<GroupModifyDTO> getGroupModifyDTO(Optional<Integer> groupId) {
        if (groupId.isEmpty()){
            return ResponseEntity.status(400).build();
        }
        String userId = securityUtil.getCurrentMemberId();
        System.out.println("ID : " + userId);

        if (userId == null || "anonymousUser".equals(userId)) {
            return ResponseEntity.status(400).build();
        }

        try {
            GroupVO group = groupMainMapper.searchGroupById(userId, groupId.get());
            if (group == null){
                System.out.println("그룹에 대한 권한이 없습니다.");
                return ResponseEntity.status(405).build();
            }
            List<CategoryVO> categoryVOList = categoryMapper.getCategories();
            GroupModifyDTO modifyDTO = new GroupModifyDTO(group, categoryVOList);
            return ResponseEntity.ok(modifyDTO);
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @Override
    public ResponseEntity<Object> modifyGroup(GroupVO group, MultipartFile fileImg) {
        String fileUrl;

        /* 수정할 이미지가 있는 경우 이미지 업로드 */
        if (fileImg != null){
            try {
                fileUrl = fileUploader.uploadFile(fileImg);
                group.setGroupImg(fileUrl);
            } catch (Exception e){
                return ResponseEntity.status(409).build();
            }
        } else {
            group.setGroupImg("default url");
        }

        /* 모임명과 카테고리가 null인 경우 에러 코드 응답 */
        if (group.getGroupName() == null || group.getCategoryId() == 0){
            return ResponseEntity.status(409).build(); // 수정 불가
        }

        try {
            /* 모임 테이블 update */
            System.out.println(group);
            groupMainMapper.updateGroup(group);

            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> registParticipation(HashMap<String, Object> paramMap) throws Exception {
        /* 파라미터 유효성 체크 */
        if (paramMap.get("groupId") == null || paramMap.get("id") == null){
            return ResponseEntity.status(405).build();
        }

//        // 참여 인원 유효성 체크
//        int groupId_int = Integer.parseInt(paramMap.get("groupId").toString());
//        ParticipationNum participationNum = groupParticipationMapper.getGroupParticipationNum(groupId_int);
//        if (participationNum.getCurNum() >= participationNum.getMaxNum()) {
//            System.out.println("참여 인원 : " + participationNum.getCurNum());
//            System.out.println("최대 인원   : " + participationNum.getMaxNum());
//            throw new Exception();
//        }

        /* 이미 참여 중이라면 추가로 넣으면 안됨 */
        int count = groupParticipationMapper.searchParticipationCount(paramMap);
        if (count > 0){
            return ResponseEntity.status(208).build(); // 이미 참여 중인 경우
        }

        int result = groupParticipationMapper.registParticipation(paramMap);
        if (result == 0){
//            return ResponseEntity.status(400).build(); // 참가 실패
            throw new Exception();
        }

        int updateResult = groupParticipationMapper.updateGroupParticipationPlus(paramMap);
        if (updateResult == 0){
//            return ResponseEntity.status(400).build();
            throw new Exception();
        }

        return ResponseEntity.ok().build();

    }

    @Override
    @Transactional
    public ResponseEntity<Object> removeParticipation(HashMap<String, Object> paramMap) throws Exception {
        /* 파라미터 유효성 체크 */
        if (paramMap.get("groupId") == null || paramMap.get("id") == null){
            return ResponseEntity.status(409).build();
        }

        /* 참여 중이지 않다면 삭제 불가 */
        int count = groupParticipationMapper.searchParticipationCount(paramMap);
        if (count == 0){
            return ResponseEntity.status(400).build(); // 해당 모임에 해당 참여자가 참여하고 있지 않은 경우
        }

        int result = groupParticipationMapper.deleteParticipation(paramMap);
        if (result == 0){
            throw new Exception();
        }

        int updateResult = groupParticipationMapper.updateGroupParticipationMinus(paramMap);
        if (updateResult == 0){
            throw new Exception();
        }

        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Object> registLikeGroup(HashMap<String, Object> paramMap) throws Exception {
        /* 파라미터 유효성 체크 */
        if (paramMap.get("groupId") == null || paramMap.get("id") == null){
            return ResponseEntity.status(409).build(); // groupId 또는 id가 없는 경우
        }

        /* groupId를 통해 해당 모임 테이블에 좋아요 +1 */
        int groupResult = groupMainMapper.updateGroupLike(paramMap);
        if (groupResult == 0){
            throw new Exception();
        }

        /* 좋아요 테이블에 행 추가 */
        int likeResult = groupMainMapper.insertLike(paramMap);
        if (likeResult == 0){
            throw new Exception();
        }
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Object> removeLikeGroup(HashMap<String, Object> paramMap) throws Exception {
        /* 파라미터 유효성 체크 */
        if (paramMap.get("groupId") == null || paramMap.get("id") == null){
            return ResponseEntity.status(409).build(); // groupId 또는 id가 없는 경우
        }

        /* 좋아요 테이블에 행 삭제 */
        int likeResult = groupMainMapper.deleteLike(paramMap);
        if (likeResult == 0){
            throw new Exception();
        }

        /* groupId를 통해 해당 모임 테이블에 좋아요 -1 */
        int groupResult = groupMainMapper.updateGroupLikeMinus(paramMap);
        if (groupResult == 0){
            throw new Exception();
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public PageDTO<GroupDTO> getMyGroups (String userId, Optional<Integer> currentPage, Optional<Integer> pageSize) {
        /* pagination에 필요한 정보 추가 */
        int pageGroupSize = 5; // 화면에 보여줄 페이지 그룹의 개수 (1, 2, 3, 4, 5)
        int curPage = currentPage.orElse(1); // 현재 페이지 번호
        int ps = pageSize.orElse(9); // 한 화면에 보여줄 객체의 수 9
        int startRow = (curPage - 1) * ps + 1;
        int endRow = curPage * ps;

        /* SQL문 parameter로 넘길 map 형성 */
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", userId);
        paramMap.put("startRow", startRow);
        paramMap.put("endRow", endRow);

        List<GroupDTO> groupDTOList = groupMainMapper.getMyGroups(paramMap);
        checkFavDate(groupDTOList);
        System.out.println(groupDTOList);
        int totalCnt = groupMainMapper.getMyGroupsCnt(paramMap);
        return new PageDTO<>(ps, pageGroupSize, curPage, totalCnt, groupDTOList);
    }

    @Override
    public PageDTO<GroupDTO> getMyParticipationGroups(String userId, Optional<Integer> currentPage, Optional<Integer> pageSize) {
        /* pagination에 필요한 정보 추가 */
        int pageGroupSize = 5; // 화면에 보여줄 페이지 그룹의 개수 (1, 2, 3, 4, 5)
        int curPage = currentPage.orElse(1); // 현재 페이지 번호
        int ps = pageSize.orElse(9); // 한 화면에 보여줄 객체의 수 9
        int startRow = (curPage - 1) * ps + 1;
        int endRow = curPage * ps;

        /* SQL문 parameter로 넘길 map 형성 */
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", userId);
        paramMap.put("startRow", startRow);
        paramMap.put("endRow", endRow);

        List<GroupDTO> groupDTOList = groupMainMapper.getMyParticipationGroups(paramMap);

        checkFavDate(groupDTOList); // dDay 세팅
        System.out.println(groupDTOList);
        int totalCnt = groupMainMapper.getMyParticipationGroupsCnt(paramMap);
        return new PageDTO<>(ps, pageGroupSize, curPage, totalCnt, groupDTOList);
    }

    @Override
    public PageDTO<GroupDTO> getMyParticipationPastGroups(String userId, Optional<Integer> currentPage, Optional<Integer> pageSize) {
        /* pagination에 필요한 정보 추가 */
        int pageGroupSize = 5; // 화면에 보여줄 페이지 그룹의 개수 (1, 2, 3, 4, 5)
        int curPage = currentPage.orElse(1); // 현재 페이지 번호
        int ps = pageSize.orElse(9); // 한 화면에 보여줄 객체의 수 9
        int startRow = (curPage - 1) * ps + 1;
        int endRow = curPage * ps;

        /* SQL문 parameter로 넘길 map 형성 */
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", userId);
        paramMap.put("startRow", startRow);
        paramMap.put("endRow", endRow);

        List<GroupDTO> groupDTOList = groupMainMapper.getMyParticipationPastGroups(paramMap);

        checkFavDate(groupDTOList); // dDay 세팅
        System.out.println(groupDTOList);
        int totalCnt = groupMainMapper.getMyParticipationPastGroupsCnt(paramMap);
        return new PageDTO<>(ps, pageGroupSize, curPage, totalCnt, groupDTOList);
    }

    @Override
    public PageDTO<GroupDTO> getMyFavoriteGroups(String userId, Optional<Integer> currentPage, Optional<Integer> pageSize) {
        /* pagination에 필요한 정보 추가 */
        int pageGroupSize = 5; // 화면에 보여줄 페이지 그룹의 개수 (1, 2, 3, 4, 5)
        int curPage = currentPage.orElse(1); // 현재 페이지 번호
        int ps = pageSize.orElse(9); // 한 화면에 보여줄 객체의 수 9
        int startRow = (curPage - 1) * ps + 1;
        int endRow = curPage * ps;

        /* SQL문 parameter로 넘길 map 형성 */
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", userId);
        paramMap.put("startRow", startRow);
        paramMap.put("endRow", endRow);

        List<GroupDTO> groupDTOList = groupMainMapper.getMyFavoriteGroups(paramMap);

        checkFavDate(groupDTOList); // dDay 세팅
        System.out.println(groupDTOList);
        int totalCnt = groupMainMapper.getMyFavoriteGroupsCnt(paramMap);
        return new PageDTO<>(ps, pageGroupSize, curPage, totalCnt, groupDTOList);
    }

    /**
     * 각 모임을 순회하며 isCurUserFavorite, dday 세팅
     * @param groupDTOList
     */
    private void checkFavDate(List<GroupDTO> groupDTOList) {
        for (GroupDTO groupDTO : groupDTOList) {
            if (groupDTO.getGroup().getGroupDate() == null){
                groupDTO.setDDay(-999); // 미정인 경우
                continue;
            }

            /* dDay 세팅 */
            LocalDate curDate = LocalDate.now();
            LocalDate targetDate = groupDTO.getGroup().getGroupDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long dayBetween = ChronoUnit.DAYS.between(curDate, targetDate);
            groupDTO.setDDay(dayBetween);
        }
    }
}

package com.scsa.moin_back.group.controller;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.group.dto.GroupDetailDTO;
import com.scsa.moin_back.group.dto.GroupModifyDTO;
import com.scsa.moin_back.group.service.IGroupDetailService;
import com.scsa.moin_back.group.service.IGroupService;
import com.scsa.moin_back.group.vo.GroupVO;
import com.scsa.moin_back.member.config.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final IGroupService groupService;
    private final IGroupDetailService groupDetailService;
    private final SecurityUtil securityUtil;

    /**
     * 모임 목록 조회 (메인)
     * @param session
     * @return
     */
    @GetMapping(value = {"/{category}/{currentPage}/{pageSize}", "/{currentPage}/{pageSize}", ""})
    public ResponseEntity<PageDTO<GroupDTO>> getGroups(HttpSession session,
                                       @PathVariable Optional<String> category,
                                       @PathVariable Optional<Integer> currentPage,
                                       @PathVariable Optional<Integer> pageSize,
                                       @RequestParam(required = false, defaultValue = "") String searchParam,
                                       @RequestParam(required = false, defaultValue = "all") String city,
                                       @RequestParam(required = false, defaultValue = "all") String district,
                                       @RequestParam(required = false, defaultValue = "Y") String isActive) {

        /* login 방식에 따라 id 가져오는 방식 변경 가능 */
//        String id = session.getAttribute("id").toString();
        String userId = securityUtil.getCurrentMemberId();
        System.out.println(userId);

        try {
            PageDTO<GroupDTO> pageDTO = groupService.getGroups(userId, currentPage, pageSize, category, searchParam, city, district, isActive);
            return ResponseEntity.ok(pageDTO);
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * 모임 목록 조회 (마이페이지)
     * @param session
     * @return
     */
    @GetMapping(value = {"/my/{currentPage}/{pageSize}", "/my"})
    public ResponseEntity<PageDTO<GroupDTO>> getMyGroups(HttpSession session,
                                                       @PathVariable Optional<Integer> currentPage,
                                                         @PathVariable Optional<Integer> pageSize) {

        /* login 방식에 따라 id 가져오는 방식 변경 가능 */
//        String id = session.getAttribute("id").toString();
        String userId = securityUtil.getCurrentMemberId();

        try {
            PageDTO<GroupDTO> pageDTO = groupService.getMyGroups(userId, currentPage, pageSize);
            return ResponseEntity.ok(pageDTO);
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }


    /**
     * 내가 참여 중인 모임 조회
     * @param session
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping(value = {"/my-participation/{currentPage}/{pageSize}", "/my-participation"})
    public ResponseEntity<PageDTO<GroupDTO>> getMyParticipation(HttpSession session,
                                                         @PathVariable Optional<Integer> currentPage,
                                                         @PathVariable Optional<Integer> pageSize) {

        /* login 방식에 따라 id 가져오는 방식 변경 가능 */
//        String id = session.getAttribute("id").toString();
        String userId = securityUtil.getCurrentMemberId();

        try {
            PageDTO<GroupDTO> pageDTO = groupService.getMyParticipationGroups(userId, currentPage, pageSize);
            return ResponseEntity.ok(pageDTO);
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * 내가 참여 중인 지난 모임 조회
     * @param session
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping(value = {"/my-participation-past/{currentPage}/{pageSize}", "/my-participation-past"})
    public ResponseEntity<PageDTO<GroupDTO>> getMyParticipationPast(HttpSession session,
                                                                @PathVariable Optional<Integer> currentPage,
                                                                @PathVariable Optional<Integer> pageSize) {

        /* login 방식에 따라 id 가져오는 방식 변경 가능 */
//        String id = session.getAttribute("id").toString();
        String userId = securityUtil.getCurrentMemberId();

        try {
            PageDTO<GroupDTO> pageDTO = groupService.getMyParticipationPastGroups(userId, currentPage, pageSize);
            return ResponseEntity.ok(pageDTO);
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * 좋아요 한 모임 조회
     * @param session
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping(value = {"/my-like/{currentPage}/{pageSize}", "/my-like"})
    public ResponseEntity<PageDTO<GroupDTO>> getMyFavoriteGroups(HttpSession session,
                                                                    @PathVariable Optional<Integer> currentPage,
                                                                    @PathVariable Optional<Integer> pageSize) {

        /* login 방식에 따라 id 가져오는 방식 변경 가능 */
//        String id = session.getAttribute("id").toString();
        String userId = securityUtil.getCurrentMemberId();

        try {
            PageDTO<GroupDTO> pageDTO = groupService.getMyFavoriteGroups(userId, currentPage, pageSize);
            return ResponseEntity.ok(pageDTO);
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }


    /**
     * 모임 상세 조회
     * @param groupId
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
    @GetMapping(value = {"/detail/{groupId}"})
    public ResponseEntity<GroupDetailDTO> getGroupDetail(@PathVariable Optional<Integer> groupId) {
        /* login 방식에 따라 id 가져오는 방식 변경 가능 */
//        String id = session.getAttribute("id").toString();
        String id = securityUtil.getCurrentMemberId();

        System.out.println(id);

        try{
            return ResponseEntity.ok(groupDetailService.getGroupDetail(groupId, id));
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * 모임 삭제 (SOFT)
     * @param paramMap
     * @return
     */
    @PutMapping("/remove")
    public ResponseEntity<Object> removeGroup(@RequestBody HashMap<String, Object> paramMap) {
        return groupService.removeGroup(paramMap);
    }

    /**
     * 모임 등록
     * @param group
     * @return
     */
    @PostMapping("/regist")
    public ResponseEntity<Object> registGroup(@RequestPart GroupVO group,
                                              @RequestPart MultipartFile fileImg) {
        /* login 방식에 따라 id 가져오는 방식 변경 가능 */
//        String id = session.getAttribute("id").toString();
        String id = securityUtil.getCurrentMemberId();
        group.setGroupLeaderId(id);

        return groupService.registGroup(group, fileImg);
    }

    /**
     * 모임 수정 - 기존 모임 정보 및 카테고리 정보 조회
     * @param groupId
     * @return
     */
    @GetMapping("/update/{groupId}")
    public ResponseEntity<GroupModifyDTO> getGroupModifyDTO(@PathVariable Optional<Integer> groupId){
        return groupService.getGroupModifyDTO(groupId);
    }

    /**
     * 모임 수정 - 수정하기
     * @param group
     * @param fileImg
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateGroup(@RequestPart GroupVO group,
                                              @RequestPart MultipartFile fileImg) {
        return groupService.modifyGroup(group, fileImg);
    }

    /**
     * 참여 테이블 행 추가
     * @param paramMap
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<Object> joinGroup(@RequestBody HashMap<String, Object> paramMap) {
        return groupService.registParticipation(paramMap);
    }

    /**
     * 참여 테이블 행 삭제
     * @param paramMap
     * @return
     */
    @DeleteMapping("/join")
    public ResponseEntity<Object> resignParticipant(@RequestBody HashMap<String, Object> paramMap) {
        try{
            return groupService.removeParticipation(paramMap);
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * 모임 좋아요 등록
     * @param paramMap
     * @return
     */
    @PostMapping("/like")
    public ResponseEntity<Object> likeGroup(@RequestBody HashMap<String, Object> paramMap){
        /* login 방식에 따라 id 가져오는 방식 변경 가능 */
//        String id = session.getAttribute("id").toString();
        String id = securityUtil.getCurrentMemberId();
        paramMap.put("id", id);
        try {
            return groupService.registLikeGroup(paramMap);
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * 모임 좋아요 삭제
     * @param paramMap
     * @return
     */
    @DeleteMapping("/like")
    public ResponseEntity<Object> unlikeGroup(@RequestBody HashMap<String, Object> paramMap){
        /* login 방식에 따라 id 가져오는 방식 변경 가능 */
//        String id = session.getAttribute("id").toString();
        String id = securityUtil.getCurrentMemberId();
        paramMap.put("id", id);
        try{
            return groupService.removeLikeGroup(paramMap);
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }
}

package com.scsa.moin_back.group.controller;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.group.dto.GroupDetailDTO;
import com.scsa.moin_back.group.service.IGroupService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final IGroupService groupService;

    /**
     * 모임 목록 조회
     * @param session
     * @return
     */
    @GetMapping(value = {"/{category}/{currentPage}/{pageSize}", ""})
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
        String userId = "testId";

        try {
            PageDTO<GroupDTO> pageDTO = groupService.getGroups(userId, currentPage, pageSize, category, searchParam, city, district, isActive);
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
    @GetMapping(value = {"/detail/{groupId}"})
    public ResponseEntity<GroupDetailDTO> getGroupDetail(@PathVariable Optional<Integer> groupId) {
        /* login 방식에 따라 id 가져오는 방식 변경 가능 */
//        String id = session.getAttribute("id").toString();
        String id = "testId";

        try{
            return ResponseEntity.ok(groupService.getGroupDetail(groupId, id));
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }
}

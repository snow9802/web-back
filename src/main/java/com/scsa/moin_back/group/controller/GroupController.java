package com.scsa.moin_back.group.controller;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.group.service.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group/*")
public class GroupController {
    private final IGroupService groupService;

    /**
     * main 페이지에 필요한 모임 정보를 리턴하는 group
     * @param session
     * @return
     */
    @GetMapping(value = {"/{page}/{category}", ""})
    public PageDTO<GroupDTO> getGroup(HttpSession session,
                                      @PathVariable Optional<Integer> page,
                                      @PathVariable Optional<String> category,
                                      @RequestParam(required = false, defaultValue = "") String searchParam,
                                      @RequestParam(required = false, defaultValue = "all") String city,
                                      @RequestParam(required = false, defaultValue = "all") String district,
                                      @RequestParam(required = false, defaultValue = "Y") String isActive) {

        /* login 방식에 따라 id 가져오는 방식 변경 가능 */
//        String id = session.getAttribute("id").toString();
        String userId = "testId";
        return groupService.getGroups(userId, page, category, searchParam, city, district, isActive);
    }
}

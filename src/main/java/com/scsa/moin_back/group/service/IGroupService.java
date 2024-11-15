package com.scsa.moin_back.group.service;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.group.dto.GroupDetailDTO;
import com.scsa.moin_back.group.dto.GroupModifyDTO;
import com.scsa.moin_back.group.vo.GroupVO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Optional;


public interface IGroupService {
    PageDTO<GroupDTO> getGroups(String userId, Optional<Integer> currentPage, Optional<Integer> pageSize, Optional<String> category, String searchParam, String city, String district, String isActive);

    ResponseEntity<Object> removeGroup(HashMap<String, Object> paramMap);

    ResponseEntity<Object> registGroup(GroupVO group);

    ResponseEntity<GroupModifyDTO> getGroupModifyDTO(Optional<Integer> groupId);

    ResponseEntity<Object> modifyGroup(GroupVO groupVO);

    ResponseEntity<Object> registParticipation(HashMap<String, Object> paramMap);
}

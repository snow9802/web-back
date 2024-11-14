package com.scsa.moin_back.group.service;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.group.mapper.GroupMainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements IGroupService {

    private final GroupMainMapper groupMainMapper;

    @Override
    public PageDTO<GroupDTO> getGroups(String userId, Optional<Integer> page, Optional<String> category, String searchParam, String city, String district, String isActive) {

        PageDTO<GroupDTO> pageDTO = new PageDTO<>();

        /* SQL문 parameter로 넘길 map 형성 */
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("searchParam", searchParam);
        paramMap.put("city", city);
        paramMap.put("district", district);
        paramMap.put("category", category.orElse("all"));

        /* 모집 여부에 따른 분기 처리 */
        if ("Y".equals(isActive)){
            List<GroupDTO> groupDTOList = groupMainMapper.getGroupsActive(paramMap);
//            System.out.println(groupDTOList);
            pageDTO.setList(groupDTOList);
            return pageDTO;
        } else {
            List<GroupDTO> groupDTOList = groupMainMapper.getGroupsNotActive(paramMap);
            pageDTO.setList(groupDTOList);
            return pageDTO;
        }
    }
}

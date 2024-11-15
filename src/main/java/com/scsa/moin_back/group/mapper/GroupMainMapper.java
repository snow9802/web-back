package com.scsa.moin_back.group.mapper;

import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.group.dto.GroupDetailDTO;
import org.apache.ibatis.annotations.Mapper;

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
}

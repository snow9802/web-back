package com.scsa.moin_back.group.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface GroupParticipationMapper {
    int registParticipation(Map<String, Object> paramMap);

    int searchParticipationCount(HashMap<String, Object> paramMap);

    int deleteParticipation(HashMap<String, Object> paramMap);

    int updateGroupParticipationPlus(HashMap<String, Object> paramMap);

    int updateGroupParticipationMinus(HashMap<String, Object> paramMap);
}

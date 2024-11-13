package com.scsa.moin_back.group.mapper;

import com.scsa.moin_back.group.vo.GroupVO;

import java.util.HashMap;
import java.util.List;

public interface GroupMapper {
    List<GroupVO> getGroups(HashMap<String, Object> params);
}

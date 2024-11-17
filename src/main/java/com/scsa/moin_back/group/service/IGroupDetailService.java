package com.scsa.moin_back.group.service;

import com.scsa.moin_back.group.dto.GroupDetailDTO;

import java.util.Optional;

public interface IGroupDetailService {
    GroupDetailDTO getGroupDetail(Optional<Integer> groupId, String id);
}

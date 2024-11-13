package com.scsa.moin_back.group.service;

import com.scsa.moin_back.group.vo.GroupVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    public List<GroupVO> getList();
}

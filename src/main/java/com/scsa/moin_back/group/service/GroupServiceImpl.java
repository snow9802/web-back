package com.scsa.moin_back.group.service;

import com.scsa.moin_back.group.mapper.GroupMapper;
import com.scsa.moin_back.group.vo.GroupVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;

    public List<GroupVO> getList() {
        /* HashMap 형태로 작성해서 mapper 메서드 호출 */
        //    int startRow;
        //    int endRow;
        //    String searchParam;
        //    String location;
        //    String isActice;
        //    session에서 현재 사용자의 id 꺼내서 던지기

        return null;
    }

}

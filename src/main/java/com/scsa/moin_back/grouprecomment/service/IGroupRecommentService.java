package com.scsa.moin_back.grouprecomment.service;

import com.scsa.moin_back.grouprecomment.vo.GroupRecommentVO;
import org.springframework.http.ResponseEntity;

public interface IGroupRecommentService {
    ResponseEntity<Object> registGroupRecomment(GroupRecommentVO groupRecomment);

    ResponseEntity<Object> modifyGroupRecomment(GroupRecommentVO groupRecomment);

    ResponseEntity<Object> removeGroupRecomment(GroupRecommentVO groupRecomment);
}

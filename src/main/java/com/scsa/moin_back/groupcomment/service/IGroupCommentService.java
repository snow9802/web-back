package com.scsa.moin_back.groupcomment.service;

import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import org.springframework.http.ResponseEntity;

public interface IGroupCommentService {
    ResponseEntity<Object> registGroupComment(GroupCommentVO paramMap);

    ResponseEntity<Object> modifyGroupComment(GroupCommentVO groupCommentVO);

    ResponseEntity<Object> removeGroupComment(GroupCommentVO groupCommentVO);
}

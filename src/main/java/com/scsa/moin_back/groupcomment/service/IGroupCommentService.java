package com.scsa.moin_back.groupcomment.service;

import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import org.springframework.http.ResponseEntity;

public interface IGroupCommentService {
    ResponseEntity<Object> registGroupComment(GroupCommentVO paramMap) throws Exception;

    ResponseEntity<Object> modifyGroupComment(GroupCommentVO groupCommentVO) throws Exception;

    ResponseEntity<Object> removeGroupComment(GroupCommentVO groupCommentVO) throws Exception;
}

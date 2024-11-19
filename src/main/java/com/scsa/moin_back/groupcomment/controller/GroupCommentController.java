package com.scsa.moin_back.groupcomment.controller;

import com.scsa.moin_back.groupcomment.service.IGroupCommentService;
import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import com.scsa.moin_back.member.config.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group/comment")
public class GroupCommentController {
    private final IGroupCommentService groupCommentService;
    private final SecurityUtil securityUtil;

    @PostMapping
    public ResponseEntity<Object> registGroupComment(@RequestBody GroupCommentVO groupCommentVO){
        groupCommentVO.setId(securityUtil.getCurrentMemberId());
        System.out.println(groupCommentVO);
        return groupCommentService.registGroupComment(groupCommentVO);
    }

    @PutMapping
    public ResponseEntity<Object> modifyGroupComment(@RequestBody GroupCommentVO groupCommentVO){
        return groupCommentService.modifyGroupComment(groupCommentVO);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteGroupComment(@RequestBody GroupCommentVO groupCommentVO){
        return groupCommentService.removeGroupComment(groupCommentVO);
    }
}

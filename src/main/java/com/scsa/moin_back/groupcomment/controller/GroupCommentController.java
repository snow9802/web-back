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
        try {
            return groupCommentService.registGroupComment(groupCommentVO);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @PutMapping
    public ResponseEntity<Object> modifyGroupComment(@RequestBody GroupCommentVO groupCommentVO){
        try {
            return groupCommentService.modifyGroupComment(groupCommentVO);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteGroupComment(@RequestBody GroupCommentVO groupCommentVO){
        try {
            return groupCommentService.removeGroupComment(groupCommentVO);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }
}

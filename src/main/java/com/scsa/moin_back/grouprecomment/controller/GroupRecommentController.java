package com.scsa.moin_back.grouprecomment.controller;


import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import com.scsa.moin_back.grouprecomment.service.IGroupRecommentService;
import com.scsa.moin_back.grouprecomment.vo.GroupRecommentVO;
import com.scsa.moin_back.member.config.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group/recomment")
public class GroupRecommentController {
    private final IGroupRecommentService groupRecommentService;
    private final SecurityUtil securityUtil;

    @PostMapping
    public ResponseEntity<Object> registGroupRecomment(@RequestBody GroupRecommentVO groupRecomment){
        groupRecomment.setId(securityUtil.getCurrentMemberId());
        return groupRecommentService.registGroupRecomment(groupRecomment);
    }

    @PutMapping
    public ResponseEntity<Object> modifyGroupRecomment(@RequestBody GroupRecommentVO groupRecomment){
        return groupRecommentService.modifyGroupRecomment(groupRecomment);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteGroupRecomment(@RequestBody GroupRecommentVO groupRecomment){
        return groupRecommentService.removeGroupRecomment(groupRecomment);
    }
}

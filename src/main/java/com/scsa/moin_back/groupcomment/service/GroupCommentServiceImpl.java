package com.scsa.moin_back.groupcomment.service;

import com.scsa.moin_back.groupcomment.mapper.GroupCommentMapper;
import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class GroupCommentServiceImpl implements IGroupCommentService{
    private final GroupCommentMapper groupCommentMapper;

    @Override
    public ResponseEntity<Object> registGroupComment(GroupCommentVO groupComment) throws Exception {
        /* groupId, 작성자 id, 내용 체크 */
        if (groupComment.getGroupId() == 0 || groupComment.getId() == null || groupComment.getCommentContent() == null) {
            return ResponseEntity.status(409).build();
        }

        int result = groupCommentMapper.insertGroupComment(groupComment);
        if (result == 0){
            throw new Exception();
        }

        int groupCommentCntResult = groupCommentMapper.updateGroupCommentCntPlus(groupComment);
        if (groupCommentCntResult == 0){
            throw new Exception();
        }

        return ResponseEntity.status(200).build();
    }

    @Override
    public ResponseEntity<Object> modifyGroupComment(GroupCommentVO groupComment) throws Exception {
        /* 댓글 id, 댓글 내용 체크 */
        if (groupComment.getCommentId() == 0 || groupComment.getCommentContent() == null) {
            return ResponseEntity.status(409).build();
        }

        int result = groupCommentMapper.updateGroupComment(groupComment);
        if (result == 0){
            throw new Exception();
        }
        return ResponseEntity.status(200).build();
    }

    @Override
    @Transactional
    public ResponseEntity<Object> removeGroupComment(GroupCommentVO groupComment) throws Exception {
        /* 댓글 id 체크 */
        if (groupComment.getCommentId() == 0) {
            return ResponseEntity.status(409).build();
        }

        int result = groupCommentMapper.deleteGroupComment(groupComment);
        if (result == 0){
            throw new Exception();
        }

        int groupCommentCntResult = groupCommentMapper.updateGroupCommentCntMinus(groupComment);
        if (groupCommentCntResult == 0){
            throw new Exception();
        }

        return ResponseEntity.status(200).build();
    }
}

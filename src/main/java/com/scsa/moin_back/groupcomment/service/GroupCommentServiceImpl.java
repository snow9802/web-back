package com.scsa.moin_back.groupcomment.service;

import com.scsa.moin_back.groupcomment.mapper.GroupCommentMapper;
import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class GroupCommentServiceImpl implements IGroupCommentService{
    private final GroupCommentMapper groupCommentMapper;

    @Override
    public ResponseEntity<Object> registGroupComment(GroupCommentVO groupComment) {
        /* groupId, 작성자 id, 내용 체크 */
        if (groupComment.getGroupId() == 0 || groupComment.getId() == null || groupComment.getCommentContent() == null) {
            return ResponseEntity.status(409).build();
        }

        try {
            groupCommentMapper.insertGroupComment(groupComment);
            return ResponseEntity.status(200).build();
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @Override
    public ResponseEntity<Object> modifyGroupComment(GroupCommentVO groupComment) {
        /* 댓글 id, 댓글 내용 체크 */
        if (groupComment.getCommentId() == 0 || groupComment.getCommentContent() == null) {
            return ResponseEntity.status(409).build();
        }

        try {
            int result = groupCommentMapper.updateGroupComment(groupComment);
            if (result == 0){
                return ResponseEntity.status(400).build(); // 수정이 이뤄지지 않음
            }
            return ResponseEntity.status(200).build();
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @Override
    public ResponseEntity<Object> removeGroupComment(GroupCommentVO groupComment) {
        /* 댓글 id 체크 */
        if (groupComment.getCommentId() == 0) {
            return ResponseEntity.status(409).build();
        }

        try {
            int result = groupCommentMapper.deleteGroupComment(groupComment);
            if (result == 0){
                return ResponseEntity.status(400).build(); // 삭제가 이뤄지지 않음
            }
            return ResponseEntity.status(200).build();
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }
}

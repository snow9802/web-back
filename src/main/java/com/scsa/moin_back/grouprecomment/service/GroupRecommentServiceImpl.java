package com.scsa.moin_back.grouprecomment.service;

import com.scsa.moin_back.grouprecomment.mapper.GroupRecommentMapper;
import com.scsa.moin_back.grouprecomment.vo.GroupRecommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupRecommentServiceImpl implements IGroupRecommentService {
    private final GroupRecommentMapper groupRecommentMapper;


    @Override
    public ResponseEntity<Object> registGroupRecomment(GroupRecommentVO groupRecomment) {
        /* commentId, 작성자 id, 내용 체크 */
        if (groupRecomment.getCommentId() == 0 || groupRecomment.getId() == null || groupRecomment.getRecommentContent() == null) {
            return ResponseEntity.status(409).build();
        }

        try {
            groupRecommentMapper.insertGroupRecomment(groupRecomment);
            return ResponseEntity.status(200).build();
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @Override
    public ResponseEntity<Object> modifyGroupRecomment(GroupRecommentVO groupRecomment) {
        /* 댓글 id, 댓글 내용 체크 */
        if (groupRecomment.getRecommentId() == 0 || groupRecomment.getRecommentContent() == null) {
            return ResponseEntity.status(409).build();
        }

        try {
            int result = groupRecommentMapper.updateGroupRecomment(groupRecomment);
            if (result == 0){
                return ResponseEntity.status(400).build(); // 수정이 이뤄지지 않음
            }
            return ResponseEntity.status(200).build();
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @Override
    public ResponseEntity<Object> removeGroupRecomment(GroupRecommentVO groupRecomment) {
        /* 댓글 id 체크 */
        if (groupRecomment.getRecommentId() == 0) {
            return ResponseEntity.status(409).build();
        }

        try {
            int result = groupRecommentMapper.deleteGroupRecomment(groupRecomment);
            if (result == 0){
                return ResponseEntity.status(400).build(); // 삭제가 이뤄지지 않음
            }
            return ResponseEntity.status(200).build();
        } catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }
}

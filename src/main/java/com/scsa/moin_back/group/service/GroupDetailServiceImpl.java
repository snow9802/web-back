package com.scsa.moin_back.group.service;

import com.scsa.moin_back.group.dto.GroupDetailDTO;
import com.scsa.moin_back.group.mapper.GroupDetailMapper;
import com.scsa.moin_back.group.vo.GroupDetailVO;
import com.scsa.moin_back.group.vo.GroupVO;
import com.scsa.moin_back.groupcomment.vo.GroupCommentVO;
import com.scsa.moin_back.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupDetailServiceImpl implements IGroupDetailService{
    private final GroupDetailMapper groupDetailMapper;

    @Override
    public GroupDetailDTO getGroupDetail(Optional<Integer> groupId, String id) {

        if (groupId.isEmpty()) {
            throw new NoSuchElementException();
        }

        List<MemberVO> groupMember = groupDetailMapper.getGroupDetailMember(groupId.get());
        List<GroupCommentVO> groupCommentVOList = groupDetailMapper.getGroupDetailComments(groupId.get());
        GroupVO group = groupDetailMapper.getGroupDetail(groupId.get());

        /* groupDetailDTO 세팅 */
        GroupDetailDTO groupDetailDTO = new GroupDetailDTO();
        GroupDetailVO groupDetailVO = new GroupDetailVO();

        groupDetailVO.setGroup(group);
        groupDetailVO.setMembers(groupMember);
        groupDetailVO.setGroupCommentList(groupCommentVOList);

        groupDetailDTO.setGroupDetailVO(groupDetailVO);

        /* 조건 처리를 통한 groupDetailDTO 추가 세팅 */
        GroupVO curGroup = groupDetailVO.getGroup();

        String isRecruit = "Y";
        String isLike = "N";
        String isParticipation = "N";
        if ("Y".equals(curGroup.getManualCloseYn()) ||
                curGroup.getGroupLimit() <= curGroup.getParticipationCount() ||
                new Date().compareTo(curGroup.getCloseDate()) > 0){ // 현재 시점이 마감 시점보다 이후
            isRecruit = "N";
        }

        if (groupDetailMapper.checkMemberLike(groupId.get(), id) != null) {
            isLike = "Y";
        }

        for (MemberVO member : groupMember) {
            if (member.getId().equals(id)) {
                isParticipation = "Y";
                break;
            }
        }

        groupDetailDTO.setIsRecruit(isRecruit);
        groupDetailDTO.setIsLike(isLike);
        groupDetailDTO.setIsParticipation(isParticipation);

        System.out.println(groupDetailDTO);
        return groupDetailDTO;
    }

}

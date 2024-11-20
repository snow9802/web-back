package com.scsa.moin_back.member.mapper;

import com.scsa.moin_back.member.vo.MemberVO;
import com.scsa.moin_back.member.vo.ModifiedMemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MypageMemberMapper {
    void removeMemberInfo(String id);
    void modifyPassword(MemberVO newPwdMember);
    void modifyInfo(MemberVO newInfoMember);
    MemberVO getMemberInfo(String id);
}

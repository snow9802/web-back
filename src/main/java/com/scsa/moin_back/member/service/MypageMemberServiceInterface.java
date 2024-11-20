package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.vo.MemberVO;
import com.scsa.moin_back.member.vo.ModifiedMemberVO;

public interface MypageMemberServiceInterface {
    // 비밀번호 확인 기능
    void checkPassword(String loginId, String password) throws Exception;
    void removeMemberById(String id) throws Exception;
    void modifyPassword(String loginId, String password) throws Exception;
    void modifyInfo(MemberVO newInfoMember) throws Exception;
    ModifiedMemberVO getMemberInfoById(String id) throws Exception;
}

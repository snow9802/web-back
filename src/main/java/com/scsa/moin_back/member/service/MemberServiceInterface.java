package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.exception.FindException;
import com.scsa.moin_back.member.vo.MemberMyVO;
import com.scsa.moin_back.member.vo.MemberVO;
import com.scsa.moin_back.member.vo.TokenInfo;

public interface MemberServiceInterface {
    // 로그인 기능
    TokenInfo login(String id, String password) throws FindException;
    // getMemberMyVO
    MemberMyVO getMemberMyVO(String id) throws FindException;
    // 아이디 찾기 기능
    String getIdByNameEmail(MemberVO member) throws FindException;
    // 아이디, 이메일 일치하는 회원 확인
    MemberVO getMemberByIdEmail(MemberVO member) throws FindException;
    // 비밀번호 변경
    void modifyPassword(MemberVO member) throws FindException;
}

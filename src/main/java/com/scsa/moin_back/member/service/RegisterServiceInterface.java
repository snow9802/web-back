package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.exception.FindException;
import com.scsa.moin_back.member.vo.MemberVO;

public interface RegisterServiceInterface {
    // 아이디 중복확인 체크
    void getidDupCheck(String id) throws FindException;
    // 닉네임 중복확인 체크
    void getnicknameDupCheck(String nickname) throws FindException;
    // 이메일 중복확인 체크
    void getemailDupCheck(String email) throws Exception;
    // 인증번호 확인
    void getCertificationNumCheck(String certificationNum, String code) throws FindException;
    // 가입 시 회원 정보 INSERT
    void registMemberInfo(MemberVO member) throws Exception;

}

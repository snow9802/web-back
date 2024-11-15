package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.exception.FindException;

public interface MemberServiceInterface {
    // 로그인 기능
    void login(String id, String password) throws FindException;
}

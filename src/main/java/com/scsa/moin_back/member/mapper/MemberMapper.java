package com.scsa.moin_back.member.mapper;

import com.scsa.moin_back.member.vo.MemberVO;

public interface MemberMapper {
    MemberVO findById(int id);
}

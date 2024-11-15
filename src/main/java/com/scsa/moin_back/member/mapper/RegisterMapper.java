package com.scsa.moin_back.member.mapper;

import com.scsa.moin_back.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {
    int getcheckById(String id);
    int getcheckByNickname(String nickname);
    int getcheckByEmail(String email);
    void registMemberInfo(MemberVO member);
}

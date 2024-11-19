package com.scsa.moin_back.member.mapper;

import com.scsa.moin_back.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberVO findById(String id);
    String getIdByNameEmail(MemberVO member);
    MemberVO getMemberByIdEmail(MemberVO member);
    void modifyPassword(MemberVO member);
    String getCategoryById(int categoryId);
    int getmadeGroupNum(String id);
}

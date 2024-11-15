package com.scsa.moin_back.member.mapper;

import com.scsa.moin_back.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

@Mapper
public interface MemberMapper {
    MemberVO findById(String id);
}

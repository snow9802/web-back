package com.scsa.moin_back.member.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JwtBlacklistMapper {
    // 블랙리스트에 토큰 추가
    void insertBlacklistToken(String token);
    // 블랙리스트에서 토큰 존재 여부 확인
    boolean isTokenBlacklisted(String token);
}

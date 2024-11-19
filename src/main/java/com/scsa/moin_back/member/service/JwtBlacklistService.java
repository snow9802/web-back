package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.mapper.JwtBlacklistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtBlacklistService implements JwtBlacklistServiceInterface{

    private final JwtBlacklistMapper jwtBlacklistMapper;

    @Override
    public boolean checkBlacklist(String token) {
        return  jwtBlacklistMapper.isTokenBlacklisted(token);
    }

    @Override
    public void registBlacklist(String token) {
        jwtBlacklistMapper.insertBlacklistToken(token);
    }
}

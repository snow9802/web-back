package com.scsa.moin_back.member.service;

public interface JwtBlacklistServiceInterface {
    boolean checkBlacklist(String token);
    void registBlacklist(String token);
}

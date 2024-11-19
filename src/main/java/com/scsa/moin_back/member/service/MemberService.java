package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.config.JwtTokenProvider;

import com.scsa.moin_back.member.exception.FindException;
import com.scsa.moin_back.member.mapper.MemberMapper;
import com.scsa.moin_back.member.vo.MemberVO;
import com.scsa.moin_back.member.vo.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberServiceInterface{
    private final MemberMapper memberMapper;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public TokenInfo login(String id, String pwd) throws FindException {
        MemberVO member = memberMapper.findById(id);
        if(member == null) {
            throw new FindException("Member not found");
        }
        System.out.println(member.checkPassword(pwd, bCryptPasswordEncoder));
        if(!member.checkPassword(pwd, bCryptPasswordEncoder)) {
            throw new FindException();
        }
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, pwd);
        System.out.println("auth 토큰 생성 완료");

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        System.out.println("authentication = " + authentication);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        System.out.println("JWT 토큰 생성 완료");
        System.out.println(tokenInfo);

        return tokenInfo;
    }

    @Override
    public String getIdByNameEmail(MemberVO member) throws FindException {
        return memberMapper.getIdByNameEmail(member);
    }

    @Override
    public MemberVO getMemberByIdEmail(MemberVO member) throws FindException {
        return memberMapper.getMemberByIdEmail(member);
    }

    @Override
    public void modifyPassword(MemberVO member) throws FindException {
        MemberVO hashedMemberInfo = member.hashPassword(bCryptPasswordEncoder);
        memberMapper.modifyPassword(hashedMemberInfo);
    }
}

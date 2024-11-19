package com.scsa.moin_back.member.config;

import com.scsa.moin_back.member.mapper.JwtBlacklistMapper;
import com.scsa.moin_back.member.mapper.MemberMapper;
import com.scsa.moin_back.member.service.JwtBlacklistService;
import com.scsa.moin_back.member.service.MemberService;
import com.scsa.moin_back.member.vo.TokenInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final JwtBlacklistService jwtBlacklistService;

    @Autowired
    public JwtTokenProvider(Environment env, JwtBlacklistService jwtBlacklistService) {
        this.jwtBlacklistService = jwtBlacklistService;
        String secretKey = env.getProperty("jwt.secret");
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 유저 정보를 가지고 AccessToken 을 생성하는 메서드
    public TokenInfo generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 권한이 비어 있는지 확인 (디버깅용)
        System.out.println("Generated Authorities: " + authorities);

        // 예외적으로 기본 권한 설정 추가 (옵션)
        if (authorities.isEmpty()) {
            authorities = "USER";
        }

        long now = (new Date()).getTime();
        System.out.println(now);
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + 864000000); //864000000
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setIssuedAt(new Date(now)) // 발급 시간 설정
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 디버깅: 토큰 내용 디코딩 및 출력
        System.out.println("Generated Access Token: " + accessToken);
        Claims decodedClaims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(accessToken).getBody();
        System.out.println("Decoded Claims: " + decodedClaims);

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);
        System.out.println("claims: " + claims);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        // 권한 리스트 출력 (디버깅용)
        System.out.println("Parsed Authorities: " + authorities);

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            System.out.println(token);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰입니다.");
            System.out.println("토큰 발급 시간: " + e.getClaims().getIssuedAt());
            System.out.println("토큰 만료 시간: " + e.getClaims().getExpiration());
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getUserIdFromToken(String token) {
        Claims c = parseClaims(token);
        return c.getSubject();
    }

    // 토큰을 블랙리스트에 추가
    public void invalidateToken(String token) {
        // 토큰을 블랙리스트에 저장
        jwtBlacklistService.registBlacklist(token);
    }

    // 토큰이 블랙리스트에 있는지 확인
    public boolean isTokenBlacklisted(String token) {
        return jwtBlacklistService.checkBlacklist(token);
    }
}
package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.mapper.MemberMapper;
import com.scsa.moin_back.member.vo.MemberVO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;

    // 의존성 주입
    public CustomUserDetailsService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        // 사용자 정보 조회 (예: ID로 찾기)
        MemberVO member = memberMapper.findById(username);

        // 사용자 없을 경우 예외 발생
        if (member == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // UserDetails 객체 반환 (패스워드는 암호화된 상태여야 함)
        return new User(member.getId(), member.getPassword(), new ArrayList<>());
    }
}

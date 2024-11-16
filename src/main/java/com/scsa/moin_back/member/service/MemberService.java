package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.exception.FindException;
import com.scsa.moin_back.member.mapper.MemberMapper;
import com.scsa.moin_back.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberServiceInterface{
    private final MemberMapper memberMapper;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public void login(String id, String pwd) throws FindException {
        MemberVO member = memberMapper.findById(id);
        if(member == null) {
            throw new FindException("Member not found");
        }
        System.out.println(member.checkPassword(pwd, bCryptPasswordEncoder));
        if(!member.checkPassword(pwd, bCryptPasswordEncoder)) {
            throw new FindException();
        }
    }
}

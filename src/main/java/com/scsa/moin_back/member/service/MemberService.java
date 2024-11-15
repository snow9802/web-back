package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.exception.FindException;
import com.scsa.moin_back.member.mapper.MemberMapper;
import com.scsa.moin_back.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberServiceInterface{
    private final MemberMapper memberMapper;

    @Override
    public void login(String id, String pwd) throws FindException {
        MemberVO member = memberMapper.findById(id);
        System.out.println(member);
        if(member == null || !member.getPassword().equals(pwd)) {
            throw new FindException();
        }
    }
}

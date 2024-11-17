package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.mapper.MemberMapper;
import com.scsa.moin_back.member.mapper.MypageMemberMapper;
import com.scsa.moin_back.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageMemberService implements MypageMemberServiceInterface {
    private final MemberMapper memberMapper;
    private final MypageMemberMapper mypageMemberMapper;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public void checkPassword(String loginId, String password) throws Exception {
        MemberVO member = memberMapper.findById(loginId);
        if (!member.checkPassword(password, bCryptPasswordEncoder)) {
            throw new Exception();
        }
    }


    @Override
    public void modifyPassword(String loginId, String password) throws Exception {
        MemberVO member = new MemberVO();
        member.setPassword(bCryptPasswordEncoder.encode(password));
        member.setId(loginId);
        System.out.println(member);
        mypageMemberMapper.modifyPassword(member);
    }

    @Override
    public void modifyInfo(MemberVO newInfoMember) throws Exception {
        mypageMemberMapper.modifyInfo(newInfoMember);
    }

    @Override
    public void removeMemberById(String id) throws Exception {
        mypageMemberMapper.removeMemberInfo(id);
    }

}

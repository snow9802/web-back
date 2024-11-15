package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.exception.FindException;
import com.scsa.moin_back.member.mapper.RegisterMapper;
import com.scsa.moin_back.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService implements RegisterServiceInterface {
    private final RegisterMapper registerMapper;
    private final MailService mailService;

    @Override
    public void getidDupCheck(String id) throws FindException {
        int result = registerMapper.getcheckById(id);
        if(result == 1){
            throw new FindException();
        }
    }

    @Override
    public void getnicknameDupCheck(String nickname) throws FindException{
        int result = registerMapper.getcheckByNickname(nickname);
        if(result == 1){
            throw new FindException();
        }
    }

    @Override
    public void getemailDupCheck(String email) throws FindException{
        int result = registerMapper.getcheckByEmail(email);
        if(result == 1){
            throw new FindException();
        }
    }

    @Override
    public void getCertificationNumCheck(String certificationNum, String code) throws FindException {
        if(!certificationNum.equals(code)) {
            throw new FindException();
        }
    }

    @Override
    public void registMemberInfo(MemberVO member) throws Exception {
        registerMapper.registMemberInfo(member);
    }

}

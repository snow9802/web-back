package com.scsa.moin_back.member.service;

import com.scsa.moin_back.member.mapper.MemberMapper;
import com.scsa.moin_back.member.mapper.MypageMemberMapper;
import com.scsa.moin_back.member.vo.MemberVO;
import com.scsa.moin_back.member.vo.ModifiedMemberVO;
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

    @Override
    public ModifiedMemberVO getMemberInfoById(String id) throws Exception {
        ModifiedMemberVO modifiedMemberVO = new ModifiedMemberVO();
        modifiedMemberVO.setId(id);
        MemberVO memberVO = mypageMemberMapper.getMemberInfo(id);
        String favCatName = memberMapper.getCategoryById(memberVO.getFavCategoryId());
        int madeGroupNum = memberMapper.getmadeGroupNum(id);

        modifiedMemberVO.setFavCategoryId(memberVO.getFavCategoryId());
        modifiedMemberVO.setName(memberVO.getName());
        modifiedMemberVO.setNickname(memberVO.getNickname());
        modifiedMemberVO.setPhoneNumber(memberVO.getPhoneNumber());
        modifiedMemberVO.setEmail(memberVO.getEmail());
        modifiedMemberVO.setProfileUrl(memberVO.getProfileUrl());

        modifiedMemberVO.setFavCategoryName(favCatName);
        modifiedMemberVO.setMadeGroupNumber(madeGroupNum);

        return modifiedMemberVO;

    }

}

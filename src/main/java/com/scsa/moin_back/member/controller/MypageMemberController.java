package com.scsa.moin_back.member.controller;

import com.scsa.moin_back.member.config.SecurityUtil;
import com.scsa.moin_back.member.service.MypageMemberService;
import com.scsa.moin_back.member.vo.MemberVO;
import com.scsa.moin_back.member.vo.ModifiedMemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my")
public class MypageMemberController {
    private final MypageMemberService mypageMemberService;
    private final SecurityUtil securityUtil;

    // 비밀번호 확인 => 이후 과정 수행 시 한번 더 확인하지 않기 때문에 무조건 이 페이지를 거쳐서 이동할 수 있게끔 해야 함!
    @PostMapping("/pwd-check")
    public ResponseEntity checkPassword(@RequestBody Map<String, String> passwordMap, HttpServletRequest request) {
        String loginId = securityUtil.getCurrentMemberId();
//        System.out.println(loginId); // id 확인용
        try {
            // 비밀번호 일치 여부 확인
            mypageMemberService.checkPassword(loginId, passwordMap.get("password"));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get-member-info")
    public ResponseEntity<ModifiedMemberVO> getMemberInfo() {
        String loginId = securityUtil.getCurrentMemberId();
        try {
            ModifiedMemberVO modifiedMemberVO = mypageMemberService.getMemberInfoById(loginId);
            return ResponseEntity.ok(modifiedMemberVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    // 비밀번호 변경
    @PostMapping("/pwd-modify")
    public ResponseEntity modifyPassword(@RequestBody Map<String, String> newpasswordMap, HttpServletRequest request) throws Exception {
        String loginId = securityUtil.getCurrentMemberId();
        try {
            mypageMemberService.modifyPassword(loginId, newpasswordMap.get("password"));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    // 회원정보 변경
    @PostMapping("/info-modify")
    public ResponseEntity modifyInfo(@RequestBody MemberVO newInfoMember, HttpServletRequest request) {
        String loginId = securityUtil.getCurrentMemberId();
        newInfoMember.setId(loginId);
        try {
            mypageMemberService.modifyInfo(newInfoMember);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    // 회원 탈퇴
    @GetMapping("/resign")
    public ResponseEntity resign(HttpServletRequest request) {
        // 로그아웃 구현 필요 ***** => 토큰 만료시키기 (근데 어차피 회원 정보 삭제하면 못찾으니깐 괜찮을거같기도 한디,, 아냐 그래도 필요할듯.
        // 회원 정보 삭제
        String loginId = securityUtil.getCurrentMemberId();
        try {
            mypageMemberService.removeMemberById(loginId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}

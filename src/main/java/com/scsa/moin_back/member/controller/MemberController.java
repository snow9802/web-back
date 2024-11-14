package com.scsa.moin_back.member.controller;

import com.scsa.moin_back.member.exception.FindException;
import com.scsa.moin_back.member.service.MemberService;
import com.scsa.moin_back.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
/**
 * 로그인, 로그아웃 기능
 */
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberVO member, HttpSession session) {
        session.removeAttribute("loginId");
        try{
            memberService.login(member.getId(), member.getPassword());
            session.setAttribute("loginId", member.getId());
            return ResponseEntity.ok().build();
        } catch (FindException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        // 로그아웃 할 때는 removeAtt 말고 세션 무효화 사용.
        session.invalidate();
    }

}

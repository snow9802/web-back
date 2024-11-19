package com.scsa.moin_back.member.controller;

import com.scsa.moin_back.member.exception.FindException;
import com.scsa.moin_back.member.service.MailServiceInterface;
import com.scsa.moin_back.member.service.RegisterServiceInterface;
import com.scsa.moin_back.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
/**
 * 회원가입 관련 기능
 */
public class RegisterController {
    private final RegisterServiceInterface registerService;
    private final MailServiceInterface mailService;

    @PostMapping("/id-check")
    public ResponseEntity checkId(@RequestBody MemberVO m) {
        try{
            registerService.getidDupCheck(m.getId());
            return ResponseEntity.ok().build();
        } catch (FindException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/nickname-check")
    public ResponseEntity checkNickname(@RequestBody MemberVO m) {
        try{
            registerService.getnicknameDupCheck(m.getNickname());
            return ResponseEntity.ok().build();
        } catch (FindException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/send-code")
    public ResponseEntity mailConfirm(@RequestBody Map<String, String> emailMap, HttpSession session) {
        try {
            registerService.getemailDupCheck(emailMap.get("email"));
            String code = mailService.sendSimpleMessage(emailMap.get("email"));
            System.out.println("사용자에게 발송한 인증코드 ==> " + code);
            // 일단 인증번호 보낸 메일 저장
            session.setAttribute("sendEmail", emailMap.get("email"));
            session.setAttribute("code", code);
            System.out.println(session.getAttribute("sendEmail"));
            System.out.println(session.getAttribute("code"));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/certi-check")
    public ResponseEntity checkCerti(@RequestBody Map<String, String> certiMap, HttpSession session) {
        try {
            String code = session.getAttribute("code").toString();
            System.out.println(code);
            registerService.getCertificationNumCheck(certiMap.get("certiNum"), code);
            // 잘 인증되었으면 인증된 메일 저장
            String confirmedEmail = session.getAttribute("sendEmail").toString();
            session.setAttribute("confirmedEmail", confirmedEmail);
            System.out.println(session.getAttribute("confirmedEmail"));
            session.removeAttribute("code");
            return ResponseEntity.ok().build();
        } catch (FindException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    /*
    1. 아이디, 닉네임, 이메일 중복확인 한번씩 더 하기
    2. 입력한 이메일 인증 후 세션에 저장해놨다가 가입하기 눌렀을 때 일치하는지 한번 더 확인
     */
    @PostMapping("/register-confirm")
    public ResponseEntity registerConfirm(@RequestBody MemberVO m, HttpSession session) {
        try{
            registerService.getidDupCheck(m.getId());
            System.out.println("아이디 통과");
            registerService.getnicknameDupCheck(m.getNickname());
            System.out.println("닉네임 통과");
            registerService.getemailDupCheck(m.getEmail());
            System.out.println("이메일 중복 통과");
            // 받은 이메일이랑 인증된 이메일이랑 다르면 insert하지 않고 바로 리턴
            // NullPointException 수정
            if (session.getAttribute("confirmedEmail") == null || !session.getAttribute("confirmedEmail").equals(m.getEmail())) {
                return ResponseEntity.badRequest().build();
            }
            System.out.println("인증번호 통과");
            registerService.registMemberInfo(m);
            System.out.println("회원 정보 등록 완료");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

}

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

//    @PostMapping("/email-check")
//    public ResponseEntity checkEmail(@RequestBody MemberVO m) {
//        try{
//            registerService.getemailDupCheck(m.getEmail());
//            return ResponseEntity.ok().build();
//        } catch (FindException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.badRequest().build();
//    }

    @PostMapping("/send-code")
    public ResponseEntity mailConfirm(@RequestBody Map<String, String> emailMap, HttpSession session) {
        try {
            registerService.getemailDupCheck(emailMap.get("email"));
            String code = mailService.sendSimpleMessage(emailMap.get("email"));
            System.out.println("사용자에게 발송한 인증코드 ==> " + code);
            session.setAttribute("code", code);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    // 인증하기 완료 후 이메일을 바꾸면? 그럼 이 결루에서 프론트에서 막아버리게 하는게 나을까요?
    // 아이디랑 닉네임
    // 그럼 이메일을 한번
    @PostMapping("/certi-check")
    public ResponseEntity checkCerti(@RequestBody Map<String, String> certiMap, HttpSession session) {
        try {
            String code = session.getAttribute("code").toString();
            System.out.println(code);
            registerService.getCertificationNumCheck(certiMap.get("certiNum"), code);
//            session.removeAttribute("code");
            return ResponseEntity.ok().build();
        } catch (FindException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    // 가입하기 성공 조건도 백에서 처리하는거 맞나..?
    // 이메일이 이전과 같은지 확인 => 다르면 이메일 exception ****
    @PostMapping("/register-confirm")
    public ResponseEntity registerConfirm(@RequestBody MemberVO m) {
        try{

            registerService.registMemberInfo(m);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

}

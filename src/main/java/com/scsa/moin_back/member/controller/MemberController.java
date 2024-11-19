package com.scsa.moin_back.member.controller;

import com.scsa.moin_back.member.config.JwtTokenProvider;
import com.scsa.moin_back.member.config.SecurityUtil;
import com.scsa.moin_back.member.exception.FindException;
import com.scsa.moin_back.member.service.JwtBlacklistService;
import com.scsa.moin_back.member.service.MailService;
import com.scsa.moin_back.member.service.MemberService;
import com.scsa.moin_back.member.vo.MemberVO;
import com.scsa.moin_back.member.vo.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
/**
 * 로그인, 로그아웃 기능
 */
public class MemberController {

    private final MemberService memberService;
    private final SecurityUtil securityUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailService mailService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberVO member) {
        try {
            TokenInfo token= memberService.login(member.getId(), member.getPassword());
            System.out.println(token);
//            String token = jwtTokenProvider.createToken(member.getId());
            System.out.println(securityUtil.getCurrentMemberId());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken());
            return new ResponseEntity<>("Login successful", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Login failed", HttpStatus.UNAUTHORIZED);
        }
    }

    // 로그아웃 시 토큰을 블랙리스트에 추가하거나 만료시킴
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // 요청 헤더에서 JWT 토큰 가져오기
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            // JWT 토큰에서 "Bearer " 제거하고 실제 토큰만 추출
            String jwtToken = token.substring(7);

            // JWT 토큰을 블랙리스트에 추가하는 로직 (예시로 invalidateToken 메서드 호출)
            tokenProvider.invalidateToken(jwtToken); // 예시 메서드

            // 클라이언트 측에서 쿠키나 로컬 스토리지에서 JWT 토큰을 제거하는 로직 필요
            // 쿠키에 저장된 토큰 삭제 (예시: HttpOnly 쿠키로 설정된 경우)
            Cookie cookie = new Cookie("token", null);
            cookie.setPath("/");
            cookie.setMaxAge(0); // 쿠키 삭제
            response.addCookie(cookie);

            return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("이미 로그아웃 상태입니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/id-search")
    public ResponseEntity<String> idSearch(@RequestBody MemberVO member, HttpServletRequest request) {
        // 접속되어있는 아이디와 입력한 아이디 동일한지 확인
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            return new ResponseEntity<>("No token provided", HttpStatus.UNAUTHORIZED);
        }
        token = token.substring(7); // "Bearer " 제거
        String id = jwtTokenProvider.getUserIdFromToken(token);
        System.out.println(id);
        try{
            String foundId = memberService.getIdByNameEmail(member);
//            System.out.println(foundId);
            if (id == null || !id.equals(foundId)) {
                // 사용자 인증 불가하거나 입력된 정보 id와 동일하지 않다면
                return ResponseEntity.badRequest().build();
            }
            return new ResponseEntity<>("Found id: " + foundId, HttpStatus.OK);
        } catch (FindException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    // 인증번호 받기
    @PostMapping("/send-code")
    public ResponseEntity mailConfirm(@RequestBody MemberVO member, HttpSession session, HttpServletRequest request) {
        try {
            // 접속되어있는 아이디와 입력한 아이디 동일한지 확인
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            token = token.substring(7); // "Bearer " 제거
            String id = jwtTokenProvider.getUserIdFromToken(token);
            if (id == null || !id.equals(member.getId())) {
                // 사용자 인증 불가하거나 입력한 아이디와 동일하지 않다면
                return ResponseEntity.badRequest().build();
            }

            // 아이디랑 이메일 일치하는 회원 있는지 확인
            MemberVO m = memberService.getMemberByIdEmail(member);
            if (m == null) {
                // 일치하는 회원 없으면
                return ResponseEntity.badRequest().build();
            }

            // 인증 이메일 전송
            String code = mailService.sendSimpleMessage(member.getEmail());
            System.out.println("사용자에게 발송한 인증코드 ==> " + code);
            // 세션에 사용자 아이디, 인증번호 보낸 이메일 및 인증번호 저장
            session.setAttribute("sendEmail", member.getEmail());
            session.setAttribute("code", code);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    // 인증하기
    @PostMapping("/code-confirm")
    public ResponseEntity codeConfirm(@RequestBody Map<String, String> codeMap, HttpSession session) {
        String sendCode = session.getAttribute("code").toString();
        String code = codeMap.get("code");
        if (sendCode.equals(code)) {
            // 전송된 코드와 동일하다면 컨펌해주고, 세션에 저장되어있던 코드랑 이메일 삭제.
            session.setAttribute("confirmedEmail", session.getAttribute("sendEmail"));
            session.removeAttribute("code");
            session.removeAttribute("sendEmail");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 비밀번호 변경하기 버튼
    @PostMapping("/reset-button")
    public ResponseEntity resetButton(@RequestBody MemberVO member, HttpSession session, HttpServletRequest request) {
        // 사용자 인증 후 아이디 동일한지 확인
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        token = token.substring(7); // "Bearer " 제거
        String id = jwtTokenProvider.getUserIdFromToken(token);
        if (id == null || !id.equals(member.getId())) {
            return ResponseEntity.badRequest().build();
        }
        // 이메일 인증된 이메일인지 확인
        String confirmedEmail = (String) session.getAttribute("confirmedEmail");
        if (confirmedEmail == null || !member.getEmail().equals(confirmedEmail)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pwd-reset")
    public ResponseEntity pwdReset(@RequestBody Map<String, String> newPasswordMap, HttpSession session, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        token = token.substring(7); // "Bearer " 제거
        String id = jwtTokenProvider.getUserIdFromToken(token);
        String newPassword = newPasswordMap.get("password");
        MemberVO m = new MemberVO();
        m.setId(id);
        m.setPassword(newPassword);
        try{
            memberService.modifyPassword(m);
            return ResponseEntity.ok().build();
        } catch (FindException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }



}

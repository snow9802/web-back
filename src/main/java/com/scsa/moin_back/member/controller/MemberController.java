package com.scsa.moin_back.member.controller;

import com.scsa.moin_back.member.config.JwtTokenProvider;
import com.scsa.moin_back.member.exception.FindException;
import com.scsa.moin_back.member.service.MemberService;
import com.scsa.moin_back.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
/**
 * 로그인, 로그아웃 기능
 */
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberVO member) {
        try {
            memberService.login(member.getId(), member.getPassword());
            String token = jwtTokenProvider.createToken(member.getId());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return new ResponseEntity<>("Login successful", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Login failed", HttpStatus.UNAUTHORIZED);
        }
    }

    // 쿠키 가져와서 만료시켜버리기 settime
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
//        System.out.println(token);
        if (token != null && token.startsWith("Bearer ")) {
            jwtTokenProvider.invalidateToken(token.substring(7));
            return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("No token provided", HttpStatus.BAD_REQUEST);
    }

}

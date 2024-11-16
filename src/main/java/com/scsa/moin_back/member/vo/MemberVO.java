package com.scsa.moin_back.member.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Getter @Setter
@RequiredArgsConstructor
@ToString
public class MemberVO {
    private String id;
    private int favCategoryId;
    private String name;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String email;
    private String profileUrl = "default url";
    private Date joinedAt;
    private String delYn = "N";

    public MemberVO hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(plainPassword, this.password);
    }

}

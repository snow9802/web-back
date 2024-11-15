package com.scsa.moin_back.member.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
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
}

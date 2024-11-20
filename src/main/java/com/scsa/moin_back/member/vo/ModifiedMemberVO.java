package com.scsa.moin_back.member.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ModifiedMemberVO {
    private String id;
    private int favCategoryId;
    private String name;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String email;
    private String profileUrl;

    private String favCategoryName;
    private int madeGroupNumber;
}

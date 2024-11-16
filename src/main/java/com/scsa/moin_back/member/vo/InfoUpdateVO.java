package com.scsa.moin_back.member.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class InfoUpdateVO {
    private String id;
    private int favCategoryId;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String profileUrl = "default url";
}

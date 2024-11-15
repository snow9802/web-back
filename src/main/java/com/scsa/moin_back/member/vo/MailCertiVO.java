package com.scsa.moin_back.member.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@RequiredArgsConstructor
@ToString
public class MailCertiVO {
    private String email;
    private String certificationNumber;
}

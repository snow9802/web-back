package com.scsa.moin_back.grouprecomment.vo;

import lombok.*;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class GroupRecommentVO {
    private int recommentId;
    private int commentId;
    private String id;
    private String writerNickname;
    private Date createdAt;
    private String recommentContent;
}

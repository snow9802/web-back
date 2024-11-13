package com.scsa.moin_back.group.vo;

import lombok.*;

import java.util.Date;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
@ToString
public class GroupVO {
    private int groupId;
    private int groupLeaderId; // FK
    private int categoryId; // FK
    private String groupName;
    private String introText;
    private String groupContent;
    private int groupLimit;
    private Date groupDate;
    private Date closeDate;
    private String manualCloseYn;
    private String city;
    private String district;
    private String detailAddr;
    private String groupImg;
    private String delYn;
    private Date createdAt;
    private int likeCount;
}

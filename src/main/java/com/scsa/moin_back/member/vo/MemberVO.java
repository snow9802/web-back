package com.scsa.moin_back.member.vo;

import java.time.LocalDateTime;

public class MemberVO {
    private int id;
    private int favCategoryId;
    private String name;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String email;
    private String profileUrl;
    private LocalDateTime joinedAt;
    private String delYn;

    public MemberVO() {
    }

    public MemberVO(int id, int favCategoryId, String name, String password, String nickname, String phoneNumber, String email, String profileUrl, LocalDateTime joinedAt, String delYn) {
        this.id = id;
        this.favCategoryId = favCategoryId;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.profileUrl = profileUrl;
        this.joinedAt = joinedAt;
        this.delYn = delYn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavCategoryId() {
        return favCategoryId;
    }

    public void setFavCategoryId(int favCategoryId) {
        this.favCategoryId = favCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "id=" + id +
                ", favCategoryId=" + favCategoryId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", joinedAt=" + joinedAt +
                ", delYn='" + delYn + '\'' +
                '}';
    }
}

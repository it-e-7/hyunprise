package com.hyunprise.backend.domain.auth.vo;

public class KakaoAccount {
    private String memberUUID;
    private String memberName;
    private String memberEmail;
    private String gender;

    public KakaoAccount(String memberUUID, String memberName, String memberEmail, String gender) {
        this.memberUUID = memberUUID;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "KakaoUserInfo{" +
                "memberUUID='" + memberUUID + '\'' +
                "memberName='" + memberName + '\'' +
                ", memberEmail='" + memberEmail + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}

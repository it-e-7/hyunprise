package com.hyunprise.backend.domain.member.vo;

import com.hyunprise.backend.domain.auth.vo.OAuthResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    private String memberUUID;
    private Long authenticationId;
    private String memberEmail;
    private String memberName;
    private Integer hPoint;
    private Integer gender;
    private Timestamp birthdate;
    private String address;
    private String contact;
    private Integer accountType;
    private Long brandId;

    public static Member fromOAuth(OAuthResponse response) {
        return Member.builder()
                .memberEmail(response.getEmail())
                .memberName(response.getName())
                .gender(response.getGender())
                .build();
    }
}
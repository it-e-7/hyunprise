package com.hyunprise.backend.domain.member.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
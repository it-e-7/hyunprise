package com.hyunprise.backend.domain.auth.vo;

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
//    private String memberPasswordHash;
    private String memberName;
    private Integer hPoint;
    private Integer gender;
    private Timestamp birthdate;
    private String address;
    private String contact;
}
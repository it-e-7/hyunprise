package com.hyunprise.backend.domain.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuedCoupon {
    private String issuedCouponUUID;
    private String memberUUID;
    private String couponUUID;
    private String couponCode;
    private Integer status;
    private Timestamp issueDate;
    private Timestamp expirationDate;
    private Integer membershipPoint;
}

package com.hyunprise.backend.domain.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponSummary {
    private String issuedCouponUUID;
    private String couponName;
    private Integer status;
    private Date expirationDate;
}

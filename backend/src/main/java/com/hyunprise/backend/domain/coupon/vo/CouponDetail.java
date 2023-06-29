package com.hyunprise.backend.domain.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDetail {
    private String issuedCouponUUID;
    private String couponName;
    private String couponDescription;
    private Integer discountType;
    private Integer discountAmount;
    private Integer discountLimit;
    private Integer minimumPurchase;
    private String retailerLocation;
    private String termsAndConditions;
    private String usageInstruction;
    private Long couponId;
    private Integer status;
    private String couponCode;
    private Date issueDate;
    private Date expirationDate;
}

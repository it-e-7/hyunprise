package com.hyunprise.backend.domain.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    private String couponUUID;
    private String sellerUUID;
    private String couponName;
    private String couponDescription;
    private Integer discountType;
    private Integer discountAmount;
    private Timestamp createdDate;
    private Integer discountLimit;
    private Integer minimumPurchase;
    private String retailerLocation;
    private String termsAndConditions;
    private String usageInstruction;
    private Integer expirationDays;
}

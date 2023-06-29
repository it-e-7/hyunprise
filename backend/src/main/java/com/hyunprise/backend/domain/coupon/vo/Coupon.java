package com.hyunprise.backend.domain.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    private Long couponId;
    private String sellerUUID;
    private String couponName;
    private String couponDescription;
    private Integer discountType;
    private Integer discountAmount;
    private Date createdDate;
    private Integer discountLimit;
    private Integer minimumPurchase;
    private String retailerLocation;
    private String termsAndConditions;
    private String usageInstruction;
}

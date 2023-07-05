package com.hyunprise.android.api.coupon.vo


import java.sql.Timestamp

data class CouponDetail(
    val issuedCouponUUID: String? = null,
    val couponName: String? = null,
    val couponDescription: String? = null,
    val discountType: Int? = null,
    val discountAmount: Int? = null,
    val discountLimit: Int? = null,
    val minimumPurchase: Int? = null,
    val retailerLocation: String? = null,
    val termsAndConditions: String? = null,
    val usageInstruction: String? = null,
    val couponUUID: String? = null,
    val status: Int? = null,
    val couponCode: String? = null,
    val issueDate: Timestamp? = null,
    val expirationDate: Timestamp? = null,
    val brandName: String? = null,
    val equivalentPoint: Int? = null

)

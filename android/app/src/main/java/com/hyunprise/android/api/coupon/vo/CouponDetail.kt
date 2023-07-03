package com.hyunprise.android.api.coupon.vo

import java.util.Date

data class CouponDetail(
    val couponUuid: String,
    val sellerUuid: String,
    val couponName: String,
    val couponDescription: String,
    val discountType: Int,
    val discountAmount: Int,
    val creationDate: Date,
    val discountLimit: Int,
    val minumumPurchase: Int,
    val retailerLocation: String,
    val termsAndConditons: String,
    val usageInstruction: String,
    val issuedCouponUuid: String,
    val memberUuid: String,
    val couponCode: String,
    val status: Int,
    val issueDate: Date,
    val expirationDate: Date
)

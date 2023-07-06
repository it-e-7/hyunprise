package com.hyunprise.android.api.coupon.vo

import java.sql.Timestamp

data class CouponSummary (
    val issuedCouponUUID: String,
    val couponName: String,
    var status: Int,
    val expirationDate: Timestamp,

    val couponUUID:String,
    val expirationDays: Int,
    val creationDate: Timestamp,
    val brandName:String
)
package com.hyunprise.android.api.coupon.vo

import java.sql.Timestamp

data class CouponSummary (
    val issuedCouponUUID: String,
    val couponName: String,
    val status: Int,
    val expirationDate: Timestamp
)
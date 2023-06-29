package com.hyunprise.android.api.coupon.vo

import java.sql.Date

data class CouponSummary (
    val issuedCouponUUID: String,
    val couponName: String,
    val status: Int,
    val expirationDate: Date
)
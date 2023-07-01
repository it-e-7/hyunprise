package com.hyunprise.android.api.coupon.vo

import java.sql.Date

data class Coupon(
    val couponUUID: String? = null,
    val sellerUUID: String? = null,
    val couponName: String? = null,
    val couponDescription: String? = null,
    val discountType: Int? = null,
    val discountAmount: Int? = null,
    val discountLimit: Int? = null,
    val minimumPurchase: Int? = null,
    val retailerLocation: String? = null,
    val termsAndConditions: String? = null,
    val usageInstruction: String? = null,
)

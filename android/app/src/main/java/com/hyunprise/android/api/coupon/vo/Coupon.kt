package com.hyunprise.android.api.coupon.vo

import java.sql.Timestamp

data class Coupon(
    val couponUUID: String? = null,
    var sellerUUID: String? = null,
    var couponName: String? = null,
    var couponDescription: String? = null,
    var discountType: Int? = null,
    var discountAmount: Int? = null,
    val creationDate: Timestamp? = null,
    var discountLimit: Int? = null,
    var minimumPurchase: Int? = null,
    val retailerLocation: String? = null,
    val termsAndConditions: String? = null,
    val usageInstruction: String? = null,
    var expirationDays: Int? = null
)

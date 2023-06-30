package com.hyunprise.android.api.coupon.vo

import java.sql.Date


data class IssuedCoupon(
    var issuedCouponUUID: String,
    var memberUUID: String,
    var couponUUID: String,
    val couponCode: String = "defaultCode",
    val status: Int = 0,
    val issueDate: Date? = null,
    val expirationDate: Date?= null
) {
    constructor(memberUUID: String, couponUUID: String)
            : this(
        issuedCouponUUID="defaultCode",
        memberUUID = memberUUID,
        couponUUID = couponUUID
    )
}


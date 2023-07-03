package com.hyunprise.android.api.coupon.vo

import java.sql.Timestamp


data class IssuedCoupon(
    var issuedCouponUUID: String,
    var memberUUID: String,
    var couponUUID: String,
    val couponCode: String = "defaultCode",
    val status: Int = 0,
    val issueDate: Timestamp? = null,
    val expirationDate: Timestamp?= null
) {
    constructor(memberUUID: String, couponUUID: String)
            : this(
        issuedCouponUUID="defaultCode",
        memberUUID = memberUUID,
        couponUUID = couponUUID
    )
}


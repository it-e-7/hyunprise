package com.hyunprise.android.ui.member.coupon.utils

import com.hyunprise.android.global.CouponConsts

object IssuedCouponStatusConverter {
    fun getStatusString(status: Int): String {
        return when (status) {
            3 -> CouponConsts.ISSUED_COUPON_STATUS_EXPIRED
            4 -> CouponConsts.ISSUED_COUPON_STATUS_USED
            else -> CouponConsts.ISSUED_COUPON_STATUS_UNKNOWN
        }
    }
}
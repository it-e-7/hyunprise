package com.hyunprise.android.api.coupon.services

import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.coupon.clients.CouponClient
import com.hyunprise.android.api.coupon.vo.Coupon
import com.hyunprise.android.api.coupon.vo.CouponDetail
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.global.api.ApiHandler

class CouponService {

    private val retrofit = RetrofitConfig.retrofit_gson
    private val couponClient = retrofit.create(CouponClient::class.java)
    
    suspend fun getOneCoupon(coupon: String) : Coupon {
        return ApiHandler.safeAPICall {
            couponClient.getOneCoupon(coupon)
        } ?: Coupon()
    }

    suspend fun postOneCoupon(coupon: Coupon) : Int {
        return ApiHandler.safeAPICall {
            couponClient.postOneCoupon(coupon)
        } ?: -1
    }

    suspend fun getAllAdminIssuedCoupons(sellerUUID: String, available: Boolean) : List<CouponSummary>? {
        return ApiHandler.safeAPICall {
            couponClient.getAllAdminIssuedCoupons(sellerUUID)
        } ?: emptyList()
    }

    suspend fun getAdminIssuedCouponByCouponUUID(couponUUID: String) : CouponDetail {
        return ApiHandler.safeAPICall {
            couponClient.getAdminIssuedCouponByCouponUUID(couponUUID)
        } ?: CouponDetail()
    }

}
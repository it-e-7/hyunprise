package com.hyunprise.android.api.coupon.services

import android.util.Log
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.coupon.clients.IssuedCouponClient
import com.hyunprise.android.api.coupon.vo.Coupon
import com.hyunprise.android.api.coupon.vo.CouponDetail
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.api.coupon.vo.IssuedCoupon
import com.hyunprise.android.global.api.ApiHandler
import retrofit2.Response

object IssuedCouponService {

    private val retrofit = RetrofitConfig.retrofit_gson
    private val issuedCouponClient = retrofit.create(IssuedCouponClient::class.java)

    suspend fun getAllCouponsOfMemberByStatus(memberUUID: String, available: Boolean): List<CouponSummary> {
        return ApiHandler.safeAPICall {
            issuedCouponClient.getAllCouponsOfMemberByStatus(memberUUID, available)
        } ?: emptyList()
    }

    suspend fun getIssuedCouponByIssuedCouponUUID(couponUUID: String): CouponDetail {
        return ApiHandler.safeAPICall {
            issuedCouponClient.getIssuedCouponByIssuedCouponUUID(couponUUID)
        } ?: CouponDetail()
    }
    
    suspend fun postIssuedCoupon(issuedCoupon: IssuedCoupon) : String {
        return ApiHandler.safeAPICall {
            issuedCouponClient.postOneIssuedCoupon(issuedCoupon)
        } ?: toString()
    }

    suspend fun getIssuedCoupon(issuedCouponUUID: String) : CouponDetail {
        return ApiHandler.safeAPICall {
            issuedCouponClient.getOneIssuedCoupon(issuedCouponUUID)
        } ?: CouponDetail()
    }
}
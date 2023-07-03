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
    
    suspend fun postIssuedCoupon(issuedCoupon: IssuedCoupon) : Response<String> {

        val response = issuedCouponClient.postOneIssuedCoupon(issuedCoupon)
        response.body() ?: emptyList<String>()
        try {
            Log.d("postIssuedCoupon", "${response.body()}")
        } catch (e: Exception) {
            Log.d("postIssuedCoupon.error", "${e.printStackTrace()}")
        }

        return response
    }

    suspend fun getIssuedCoupon(issuedCouponUUID: String) : Response<CouponDetail> {

        val response = issuedCouponClient.getOneIssuedCoupon(issuedCouponUUID)
        response.body() ?: emptyList<String>()
        try {
            Log.d("getIssuedCoupon", "${response.body()}")
        } catch (e: Exception) {
            Log.d("getIssuedCoupon.error", "${e.printStackTrace()}")
        }

        return response
    }
}
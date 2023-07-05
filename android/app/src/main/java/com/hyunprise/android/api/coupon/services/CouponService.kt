package com.hyunprise.android.api.coupon.services

import android.util.Log
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.coupon.clients.CouponClient
import com.hyunprise.android.api.coupon.clients.IssuedCouponClient
import com.hyunprise.android.api.coupon.vo.Coupon
import com.hyunprise.android.api.coupon.vo.CouponDetail
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.api.coupon.vo.IssuedCoupon
import com.hyunprise.android.global.api.ApiHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object CouponService {

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
}
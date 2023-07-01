package com.hyunprise.android.api.coupon.services

import android.util.Log
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.coupon.clients.CouponClient
import com.hyunprise.android.api.coupon.clients.IssuedCouponClient
import com.hyunprise.android.api.coupon.vo.Coupon
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.api.coupon.vo.IssuedCoupon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CouponService {

    private val retrofit = RetrofitConfig.retrofit_gson
    private val issuedCouponClient = retrofit.create(CouponClient::class.java)
    
    suspend fun getOneCoupon(coupon: String) : Response<Coupon> {
        val response = issuedCouponClient.getOneCoupon(coupon)
        response.body() ?: emptyList<String>()

        return  response
    }
}
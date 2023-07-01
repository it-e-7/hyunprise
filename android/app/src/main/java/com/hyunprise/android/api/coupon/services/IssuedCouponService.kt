package com.hyunprise.android.api.coupon.services

import android.util.Log
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.coupon.clients.IssuedCouponClient
import com.hyunprise.android.api.coupon.vo.Coupon
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.api.coupon.vo.IssuedCoupon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class IssuedCouponService {

    private val retrofit = RetrofitConfig.retrofit_gson
    private val issuedCouponClient = retrofit.create(IssuedCouponClient::class.java)
    suspend fun fetchData(memberUUID: String, status: Int): List<CouponSummary> {
        return withContext(Dispatchers.Main) {
            val response = issuedCouponClient.getAllCouponsOfMemberByStatus(memberUUID, available)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            }
            else {
                Log.d("sychoi", "Error ${response.raw()}")
                emptyList()
            }
        }
    }
    
    suspend fun postIssuedCoupon(issuedCoupon: IssuedCoupon) : Response<Coupon> {
        val response = issuedCouponClient.postOneIssuedCoupon(issuedCoupon)
        response.body() ?: emptyList<String>()

        return  response
    }
}
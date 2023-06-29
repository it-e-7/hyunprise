package com.hyunprise.android.api.coupon.services

import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.coupon.clients.IssuedCouponClient
import com.hyunprise.android.api.coupon.vo.CouponSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IssuedCouponService {

    private val retrofit = RetrofitConfig.retrofit_gson
    private val issuedCouponClient = retrofit.create(IssuedCouponClient::class.java)
    suspend fun fetchData(memberUUID: String, status: Int): List<CouponSummary> {
        return withContext(Dispatchers.Main) {
            val response = issuedCouponClient.getAllCouponsOfMemberByStatus(memberUUID, status)
            response.body() ?: emptyList()
        }
    }
}
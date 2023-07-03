package com.hyunprise.android.api.coupon.services

import android.util.Log
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.coupon.clients.IssuedCouponClient
import com.hyunprise.android.api.coupon.vo.Coupon
import com.hyunprise.android.api.coupon.vo.CouponDetail
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.api.coupon.vo.IssuedCoupon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class IssuedCouponService {

    private val retrofit = RetrofitConfig.retrofit_gson
    private val issuedCouponClient = retrofit.create(IssuedCouponClient::class.java)

/*    suspend fun fetchData(memberUUID: String, status: Int): List<CouponSummary> {
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
    }*/
    
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
package com.hyunprise.android.api.coupon.services

import android.util.Log
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.coupon.interfaces.IssuedCouponInterface
import com.hyunprise.android.api.coupon.vo.CouponSummary
import retrofit2.Call
import retrofit2.Response

class IssuedCouponService {

    private val retrofit = RetrofitConfig.retrofit_gson
    private val apiInterface = retrofit.create(IssuedCouponInterface::class.java)

    fun run() {
        val memberUUID = "FF1342115E49E60FE05304001CACF958"
        val status = 1
        val call: Call<List<CouponSummary>> = apiInterface.getAllCouponsOfMemberByStatus(memberUUID, status)
        Log.d("sychoi", "inside run")
        call.enqueue(object: retrofit2.Callback<List<CouponSummary>> {
            override fun onResponse(
                call: Call<List<CouponSummary>>,
                response: Response<List<CouponSummary>>
            ) {
                val coupons = response.body()
                Log.d("sychoi", coupons.toString())
            }

            override fun onFailure(call: Call<List<CouponSummary>>, t: Throwable) {
                Log.d("sychoi", t.message.toString())
            }
        })
    }
}
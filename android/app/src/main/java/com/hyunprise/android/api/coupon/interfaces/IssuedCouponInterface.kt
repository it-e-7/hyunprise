package com.hyunprise.android.api.coupon.interfaces

import com.hyunprise.android.api.coupon.vo.CouponSummary
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IssuedCouponInterface {
    @GET("issued_coupon")
    fun getAllCouponsOfMemberByStatus (
        @Query("memberUUID") memberUUID: String,
        @Query("status") status: Int
    ): Call<List<CouponSummary>>

}
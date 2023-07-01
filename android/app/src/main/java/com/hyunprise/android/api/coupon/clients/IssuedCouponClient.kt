package com.hyunprise.android.api.coupon.clients

import com.hyunprise.android.api.coupon.vo.Coupon
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.api.coupon.vo.IssuedCoupon
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface IssuedCouponClient {
    @Headers("Content-Type: application/json")
    @GET("issued_coupon")
    suspend fun getAllCouponsOfMemberByStatus (
        @Query("memberUUID") memberUUID: String,
        @Query("available") available: Boolean
    ): Response<List<CouponSummary>>

    @Headers("Content-Type: application/json")
    @POST("issued_coupon")
    suspend fun postOneIssuedCoupon(@Body issuedCoupon: IssuedCoupon): Response<String>
}

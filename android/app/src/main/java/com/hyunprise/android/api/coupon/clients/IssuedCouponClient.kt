package com.hyunprise.android.api.coupon.clients

import com.hyunprise.android.api.coupon.vo.CouponDetail
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.api.coupon.vo.IssuedCoupon
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IssuedCouponClient {
    @Headers("Content-Type: application/json")
    @GET("issued_coupon")
    suspend fun getAllCouponsOfMemberByStatus (
        @Query("memberUUID") memberUUID: String,
        @Query("available") available: Boolean
    ): Response<List<CouponSummary>>

    @GET("issued_coupon/count")
    suspend fun getAvailableIssuedCouponCount (
        @Query("memberUUID") memberUUID: String
    ): Response<Int>

    @Headers("Content-Type: application/json")
    @GET("issued_coupon/{uuid}")
    suspend fun getIssuedCouponByIssuedCouponUUID (
       @Path("uuid") issuedCouponUUID: String
    ): Response<CouponDetail>

    @Headers("Content-Type: application/json")
    @POST("issued_coupon")
    suspend fun postOneIssuedCoupon(@Body issuedCoupon: IssuedCoupon): Response<String>

    @Headers("Content-Type: application/json")
    @GET("issued_coupon/{issuedCouponUUID}")
    suspend fun getOneIssuedCoupon (
        @Path("issuedCouponUUID") issuedCouponUUID: String,
    ): Response<CouponDetail>

}

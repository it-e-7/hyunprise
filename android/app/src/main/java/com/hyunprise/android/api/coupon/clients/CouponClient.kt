package com.hyunprise.android.api.coupon.clients

import com.hyunprise.android.api.coupon.vo.Coupon
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.api.coupon.vo.IssuedCoupon
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CouponClient {
    @Headers("Content-Type: application/json")
    @GET("coupon/{couponUUID}")
    suspend fun getOneCoupon(
        @Path("couponUUID") couponUUID: String,
    ): Response<Coupon>
}

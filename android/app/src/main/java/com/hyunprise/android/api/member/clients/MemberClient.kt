package com.hyunprise.android.api.member.clients

import com.hyunprise.android.api.member.vo.Member
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface MemberClient {
    @Headers("Content-Type: application/json")
    @GET("member/me")
    suspend fun getMemberMe(): Response<Member>
}

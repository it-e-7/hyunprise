package com.hyunprise.android.ui.member.login.serivce

import com.hyunprise.android.ui.member.login.vo.TokenResponse
import com.kakao.sdk.auth.model.OAuthToken
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @POST("api/v1/oauth/authorize")
    fun authorize(@Query("token") token:String): Call<String>
}
package com.hyunprise.android.api.oauth.clients

import com.hyunprise.android.api.oauth.vo.OAuth
import com.hyunprise.android.api.oauth.vo.OAuthResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OAuthClient {
    @Headers("Content-Type: application/json")
    @POST("auth")
    suspend fun authorize(@Body oAuth: OAuth): Response<OAuthResult>
}
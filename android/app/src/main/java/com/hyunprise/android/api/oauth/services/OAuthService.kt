package com.hyunprise.android.api.oauth.services

import android.util.Log
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.oauth.clients.OAuthClient
import com.hyunprise.android.api.oauth.vo.OAuth
import com.hyunprise.android.api.oauth.vo.OAuthResult
import com.hyunprise.android.global.api.ApiHandler

class OAuthService {
    private val retrofit = RetrofitConfig.retrofit_gson
    private val oAuthClient = retrofit.create(OAuthClient::class.java)
    suspend fun getOAuthResult(oAuth: OAuth): OAuthResult {
        Log.d("log.login", "oAuth obj $oAuth")
        return ApiHandler.safeAPICall {
            oAuthClient.authorize(oAuth)
        } ?: OAuthResult()
    }
}

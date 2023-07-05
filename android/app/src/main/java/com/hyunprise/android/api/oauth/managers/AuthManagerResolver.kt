package com.hyunprise.android.api.oauth.managers

import com.hyunprise.android.api.oauth.vo.OAuthProvider

object AuthManagerResolver {
    fun resolve(oAuthProvider: OAuthProvider): AuthManager {
        when (oAuthProvider) {
            OAuthProvider.KAKAO -> return KakaoAuthManager
            else -> {
                throw IllegalArgumentException("Invalid or Unsupported OAuthProvider")
            }
        }
    }
}
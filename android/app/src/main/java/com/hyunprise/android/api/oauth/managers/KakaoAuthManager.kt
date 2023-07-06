package com.hyunprise.android.api.oauth.managers

import android.content.Context
import android.util.Log
import com.hyunprise.android.api.oauth.services.OAuthService
import com.hyunprise.android.api.oauth.vo.OAuth
import com.hyunprise.android.api.oauth.vo.OAuthProvider
import com.hyunprise.android.api.oauth.vo.OAuthResult
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

object KakaoAuthManager: AuthManager {

    private val kakaoAccountLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("log.login", "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("log.login", "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }

    override fun attemptLogin(context: Context, authorizeCallback: (oAuthProvider: OAuthProvider) -> Unit) {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable( context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("log.login", "카카오톡으로 로그인 실패", error)
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoAccountLoginCallback)
                } else if (token != null) {
                    Log.i("log.login", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    authorizeCallback(OAuthProvider.KAKAO)
                }
            }
        } else {
            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoAccountLoginCallback)
        }
    }
    override fun getAccessToken(): String? {
        return TokenManagerProvider.instance.manager.getToken()?.accessToken
    }
    override fun hasValidToken(): Boolean {
        var isValid = false
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error == null) {
                    isValid = true
                    return@accessTokenInfo
                }
            }
        }
        return isValid
    }

    override fun logout(callback: () -> Unit) {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("log.login", "로그아웃 실패.", error)
            } else {
                Log.i("log.login", "로그아웃 성공.")
            }
            callback()
        }
    }
    override fun unlink(callback: () -> Unit) {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e("log.login", "연동해제 실패.", error)
            } else {
                Log.i("log.login", "연동해제 성공. SDK에서 토큰 삭제됨")
            }
            callback()
        }
    }

    private fun makeOAuth(): OAuth {
        return OAuth(null,  OAuthProvider.KAKAO, getAccessToken())
    }

    override suspend fun authorize(): OAuthResult {
        val oAuth = makeOAuth()
        return OAuthService().getOAuthResult(oAuth)
    }
}
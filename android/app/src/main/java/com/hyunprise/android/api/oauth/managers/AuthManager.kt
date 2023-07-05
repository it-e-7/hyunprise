package com.hyunprise.android.api.oauth.managers

import android.content.Context
import com.hyunprise.android.api.oauth.vo.OAuthProvider
import com.hyunprise.android.api.oauth.vo.OAuthResult

interface AuthManager {
    fun getAccessToken(): String?
    fun hasValidToken(): Boolean
    fun logout(callback: () -> Unit)
    fun unlink(callback: () -> Unit)
    fun attemptLogin(context: Context, authorizeCallback: (oAuthProvider: OAuthProvider) -> Unit)
    suspend fun authorize(): OAuthResult
}
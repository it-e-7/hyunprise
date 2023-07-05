package com.hyunprise.android.store

import android.content.Context
import androidx.preference.PreferenceManager
import com.hyunprise.android.api.oauth.vo.OAuthProvider

private const val KEY_LOGIN_TYPE = "key_login"
class MemberSharedPreferences(context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = preferences.edit()

    fun getSavedOAuthProvider(): OAuthProvider? {
        val loginType = preferences.getString(KEY_LOGIN_TYPE, "") ?: ""
        return try {
            OAuthProvider.valueOf(loginType)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
    fun saveOAuthProvider(provider: OAuthProvider?) {
        val loginType = provider?.name ?: ""
        editor.putString(KEY_LOGIN_TYPE, loginType)
        editor.apply()
    }
    fun clearLoginType() {
        saveOAuthProvider(null)
    }


}
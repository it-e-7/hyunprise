package com.hyunprise.android.store

import android.content.Context
import androidx.preference.PreferenceManager
import com.hyunprise.android.api.member.vo.Member
import com.hyunprise.android.api.oauth.vo.OAuthProvider

enum class MemberKey {
    LOGIN_TYPE, MEMBER_UUID, MEMBER_ACCOUNT_TYPE, MEMBER_NAME, H_POINT
}

class MemberSharedPreferences(context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = preferences.edit()

    fun getSavedOAuthProvider(): OAuthProvider? {
        return getStringProperty(MemberKey.LOGIN_TYPE)?.let { loginType ->
            try {
                OAuthProvider.valueOf(loginType)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
    fun saveOAuthProvider(provider: OAuthProvider?) {
        val loginType = provider?.name ?: ""
        saveStringProperty(MemberKey.LOGIN_TYPE, loginType)
    }

    private fun getStringProperty(property: MemberKey): String? {
        return preferences.getString(property.name, null)
    }
    private fun saveStringProperty(property: MemberKey, value: String) {
        editor.putString(property.name, value)
        editor.apply()
    }
    private fun getIntProperty(property: MemberKey): Int {
        return preferences.getInt(property.name, -1)
    }
    private fun saveIntProperty(property: MemberKey, value: Int) {
        editor.putInt(property.name, value)
        editor.apply()
    }
    fun setMemberProperty(member: Member) {
        saveStringProperty(MemberKey.MEMBER_NAME, member.memberName!!)
        saveStringProperty(MemberKey.MEMBER_UUID, member.memberUUID!!)
        saveIntProperty(MemberKey.MEMBER_ACCOUNT_TYPE, member.accountType!!)
        saveIntProperty(MemberKey.H_POINT, member.membershipPoint!!)
    }
    fun getMemberUUID(): String? {
        return getStringProperty(MemberKey.MEMBER_UUID)
    }
    fun getMemberName(): String? {
        return getStringProperty(MemberKey.MEMBER_NAME)
    }
    fun getAccountType(): Int? {
        val value = getIntProperty(MemberKey.MEMBER_ACCOUNT_TYPE)
        return if (value != -1) value else null
    }
    fun getMembershipPoint(): Int? {
        val value = getIntProperty(MemberKey.H_POINT)
        return if (value != -1) value else null
    }
    fun clearMemberProperty() {
        editor.remove(MemberKey.LOGIN_TYPE.name)
        editor.remove(MemberKey.MEMBER_UUID.name)
        editor.remove(MemberKey.MEMBER_ACCOUNT_TYPE.name)
        editor.remove(MemberKey.MEMBER_NAME.name)
        editor.remove(MemberKey.H_POINT.name)
        editor.apply()
    }
}
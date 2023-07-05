package com.hyunprise.android.store

import android.content.Context
import androidx.preference.PreferenceManager

private const val KEY_PERMISSION_GUIDE_SHOWN = "key_pg_shown"

class EnvSharedPreferences(context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = preferences.edit()

    fun getPermissionGuideShown(): Boolean {
        return preferences.getBoolean(KEY_PERMISSION_GUIDE_SHOWN, false)
    }
    fun setPermissionGuideShown(shown: Boolean) {
        editor.putBoolean(KEY_PERMISSION_GUIDE_SHOWN, shown)
        editor.apply()
    }


}
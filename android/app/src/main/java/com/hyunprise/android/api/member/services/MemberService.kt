package com.hyunprise.android.api.member.services

import android.content.Context
import android.util.Log
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.member.clients.MemberClient
import com.hyunprise.android.api.member.vo.Member
import com.hyunprise.android.global.api.ApiHandler
import com.hyunprise.android.store.MemberSharedPreferences

class MemberService {

    private val retrofit = RetrofitConfig.retrofit_gson
    private val memberClient = retrofit.create(MemberClient::class.java)

    suspend fun getMemberMe(): Member {
        Log.d("log.login", "calling API Handler")
        return ApiHandler.safeAPICall {
            memberClient.getMemberMe()
        } ?: Member()
    }

    suspend fun updateLoggedInMemberData(context: Context) {
        val member = getMemberMe()
        Log.d("log.login", "saving member info $member")
        MemberSharedPreferences(context).setMemberProperty(member)
    }

}
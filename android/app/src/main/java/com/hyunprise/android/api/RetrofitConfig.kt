package com.hyunprise.android.api

import com.hyunprise.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    private const val BASE_URL = BuildConfig.BACKEND_BASE_URL
    private val gson: GsonConverterFactory = GsonConverterFactory.create()
    val retrofit_gson: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(gson)
        .build()
}
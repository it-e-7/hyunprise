package com.hyunprise.android.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hyunprise.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    private const val BASE_URL = BuildConfig.BACKEND_BASE_URL

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    val retrofit_gson: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

}
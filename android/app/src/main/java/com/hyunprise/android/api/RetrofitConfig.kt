package com.hyunprise.android.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    private const val BASE_URL = "http://10.0.2.2:8080/api/"
    private val gson: GsonConverterFactory = GsonConverterFactory.create()
    val retrofit_gson: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(gson)
        .build()
}
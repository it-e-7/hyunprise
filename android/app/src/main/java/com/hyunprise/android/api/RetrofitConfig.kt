package com.hyunprise.android.api


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    private const val BASE_URL = "https://2d9b-112-221-184-61.ngrok-free.app/api/"
    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    val retrofit_gson: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}
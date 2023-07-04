package com.hyunprise.android.api

import com.hyunprise.android.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    private const val BASE_URL = BuildConfig.BACKEND_BASE_URL
    private val gson: GsonConverterFactory = GsonConverterFactory.create()
    var retrofit_gson: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(gson)
        .build()

    private class HeaderInterceptor(private val token: String): Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val newRequest = request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            return chain.proceed(newRequest)
        }
    }

    fun patchAuthorizationHeader(token: String) {
        retrofit_gson = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HeaderInterceptor(token)) // Add the HeaderInterceptor
                    .build()
            )
            .addConverterFactory(gson)
            .build()
    }
}
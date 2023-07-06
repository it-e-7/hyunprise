package com.hyunprise.android.global.api

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object ApiHandler {
    suspend inline fun <T> safeAPICall(crossinline apiCall: suspend () -> Response<T>): T? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    Log.d("log.apicall", "Success. ${response.body()}")
                    response.body()
                } else {
                    Log.d("log.apicall", "Got response but failed. $response")
                    null
                }
            } catch (e: Exception) {
                Log.d("log.apicall", "API call Error ${e.message}")
                null
            }
        }
    }
}
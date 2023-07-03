package com.hyunprise.android.global.api

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object ApiHandler {
    suspend inline fun <T> safeAPICall(crossinline apiCall: suspend () -> Response<T>): T? {
        return withContext(Dispatchers.Main) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.d("sychoi", e.message.toString())
                null
            }
        }
    }
}
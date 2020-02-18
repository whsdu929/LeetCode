package com.xudadong.leetcode.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * @author sunfusheng
 * @since 2020-01-03
 */
object CodeFileFetcher {
    private var mOkHttpClient: OkHttpClient? = null
    private const val TIMEOUT: Long = 60

    private fun getOkHttpClient(): OkHttpClient? {
        if (mOkHttpClient == null) {
            mOkHttpClient = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        }
        return mOkHttpClient
    }

    suspend fun fetch(url: String): String? {
        return GlobalScope.async(Dispatchers.Default) {
            val request = Request.Builder().url(url).get().build();
            val newCall = getOkHttpClient()
                ?.newCall(request)
            val response = newCall?.execute()
            return@async response?.body?.string()
        }.await()
    }
}
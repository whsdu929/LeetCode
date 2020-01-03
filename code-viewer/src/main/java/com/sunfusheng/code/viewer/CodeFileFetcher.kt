package com.sunfusheng.code.viewer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * @author sunfusheng
 * @since 2020-01-03
 */
object CodeFileFetcher {

    suspend fun fetch(url: String): String? {
        return GlobalScope.async(Dispatchers.Default) {
            val request = Request.Builder().url(url).get().build();
            val newCall = OkHttpClient().newCall(request)
            val response = newCall.execute()
            return@async response.body?.string()
        }.await()
    }
}
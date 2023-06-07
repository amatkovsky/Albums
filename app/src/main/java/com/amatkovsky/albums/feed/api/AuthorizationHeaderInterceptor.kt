package com.amatkovsky.albums.feed.api

import com.amatkovsky.albums.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationHeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val clientId = BuildConfig.UNSPLASH_CLIENT_ID
        return chain.request().newBuilder()
            .addHeader("Authorization", "Client-ID $clientId")
            .build()
            .let { request ->
                chain.proceed(request)
            }
    }
}

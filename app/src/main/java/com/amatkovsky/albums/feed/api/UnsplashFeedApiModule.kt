package com.amatkovsky.albums.feed.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class UnsplashFeedApiModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationHeaderInterceptor())
            .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .client(okHttpClient)
            .build()

    @Provides
    fun unsplashFeedApi(retrofit: Retrofit): UnsplashFeedApi =
        retrofit.create(UnsplashFeedApi::class.java)
}

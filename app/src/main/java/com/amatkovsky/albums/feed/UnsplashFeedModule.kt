package com.amatkovsky.albums.feed

import com.amatkovsky.albums.feed.api.AuthorizationHeaderInterceptor
import com.amatkovsky.albums.feed.api.UnsplashFeedApi
import com.amatkovsky.albums.feed.service.RealUnsplashFeedService
import com.amatkovsky.albums.feed.service.UnsplashFeedService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
abstract class UnsplashFeedModule {

    @Binds
    abstract fun unsplashFeedService(impl: RealUnsplashFeedService): UnsplashFeedService

    companion object {
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
}

package com.amatkovsky.albums.feed.api

import retrofit2.http.GET

interface UnsplashFeedApi {

    @GET("photos")
    suspend fun photos(): List<PhotoDTO>

}

package com.amatkovsky.albums.feed.api

import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashFeedApi {

    @GET("photos")
    suspend fun photos(@Query("page") page: Int, @Query("per_page") perPage: Int): List<PhotoDTO>

}

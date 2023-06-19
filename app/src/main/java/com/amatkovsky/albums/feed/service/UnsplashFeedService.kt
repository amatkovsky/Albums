package com.amatkovsky.albums.feed.service

import com.amatkovsky.albums.feed.api.UnsplashFeedApi
import com.amatkovsky.albums.feed.domain.Photo
import com.amatkovsky.albums.feed.domain.toPhoto
import javax.inject.Inject

interface UnsplashFeedService {

    suspend fun getPhotos(page: Int, pageSize: Int): List<Photo>
}

class RealUnsplashFeedService @Inject constructor(
    private val unsplashFeedApi: UnsplashFeedApi,
) : UnsplashFeedService {

    override suspend fun getPhotos(page: Int, pageSize: Int): List<Photo> {
        return unsplashFeedApi.photos(page = page, perPage = pageSize).map { it.toPhoto() }
    }
}

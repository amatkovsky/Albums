package com.amatkovsky.albums.feed.domain

data class Photo(
    val id: String,
    val description: String?,
    val width: Int,
    val height: Int,
    val urls: PhotoUrls,
)

data class PhotoUrls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
)

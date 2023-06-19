package com.amatkovsky.albums.feed.domain

import com.amatkovsky.albums.feed.api.PhotoDTO
import com.amatkovsky.albums.feed.api.UrlsDTO

fun PhotoDTO.toPhoto(): Photo {
    return Photo(
        id = id,
        description = description,
        width = width,
        height = height,
        urls = urls.toPhotoUrls(),
    )
}

fun UrlsDTO.toPhotoUrls(): PhotoUrls {
    return PhotoUrls(
        raw = raw,
        full = full,
        regular = regular,
        small = small,
        thumb = thumb,
    )
}

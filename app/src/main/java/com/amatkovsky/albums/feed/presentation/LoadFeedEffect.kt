package com.amatkovsky.albums.feed.presentation

import com.amatkovsky.albums.feed.service.UnsplashFeedService
import com.amatkovsky.albums.unidirectional.Action
import com.amatkovsky.albums.unidirectional.Effect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadFeedEffect @Inject constructor(
    private val unsplashFeedService: UnsplashFeedService,
) : Effect<FeedState> {

    override fun observe(actions: Flow<Action>, state: () -> FeedState): Flow<Action> {
        return actions.filterIsInstance<PaginateFeedAction>()
            .map { action ->
                unsplashFeedService.getPhotos(page = action.page, pageSize = action.pageSize)
                    .let {
                        FeedLoadedAction(
                            photos = it.map { photo -> photo.id },
                            page = action.page,
                        )
                    }
            }
    }

}

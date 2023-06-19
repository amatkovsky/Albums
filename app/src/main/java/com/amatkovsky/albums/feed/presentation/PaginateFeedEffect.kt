package com.amatkovsky.albums.feed.presentation

import com.amatkovsky.albums.unidirectional.Action
import com.amatkovsky.albums.unidirectional.Effect
import com.amatkovsky.albums.unidirectional.Initial
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class PaginateFeedEffect @Inject constructor() : Effect<FeedState> {
    override fun observe(actions: Flow<Action>, state: () -> FeedState): Flow<Action> {
        return actions
            .filter { it is LoadMoreAction || it is Initial }
            .mapNotNull {
                val state = state()
                if (state.paginationState != PaginationState.IDLE) {
                    return@mapNotNull null
                }
                when (state) {
                    FeedState.Loading    -> PaginateFeedAction(
                        page = 1,
                        pageSize = 30,
                    )

                    is FeedState.Success -> PaginateFeedAction(
                        page = state.page + 1,
                        pageSize = 30,
                    )
                }
            }
    }
}

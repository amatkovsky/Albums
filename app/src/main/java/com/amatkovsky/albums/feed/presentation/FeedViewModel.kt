package com.amatkovsky.albums.feed.presentation

import com.amatkovsky.albums.unidirectional.Action
import com.amatkovsky.albums.unidirectional.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

object LoadMoreAction : Action

data class PaginateFeedAction(
    val page: Int,
    val pageSize: Int,
) : Action

data class FeedLoadedAction(
    val photos: List<String>,
    val page: Int,
) : Action

enum class PaginationState {
    IDLE,
    LOADING,
    END,
}

sealed class FeedState {
    abstract val paginationState: PaginationState

    object Loading : FeedState() {
        override val paginationState = PaginationState.LOADING
    }

    data class Success(
        val photos: List<String>,
        override val paginationState: PaginationState,
        val page: Int,
    ) : FeedState()
}

@HiltViewModel
class FeedViewModel @Inject constructor(
    loadFeedEffect: LoadFeedEffect,
    paginateFeedEffect: PaginateFeedEffect,
) : BaseViewModel<FeedState>(FeedState.Loading, listOf(loadFeedEffect, paginateFeedEffect)) {

    override fun reduce(state: FeedState, action: Action): FeedState {
        return when (action) {
            is PaginateFeedAction -> when (state) {
                is FeedState.Loading -> error("Invalid state $state")
                is FeedState.Success -> state.copy(paginationState = PaginationState.LOADING)
            }

            is FeedLoadedAction -> when (state) {
                is FeedState.Loading -> FeedState.Success(
                    photos = action.photos,
                    page = action.page,
                    paginationState = PaginationState.IDLE,
                )
                is FeedState.Success -> FeedState.Success(
                    photos = state.photos + action.photos,
                    page = action.page,
                    paginationState = if (action.photos.isEmpty()) PaginationState.END else PaginationState.IDLE,
                )
            }

            else -> state
        }
    }
}

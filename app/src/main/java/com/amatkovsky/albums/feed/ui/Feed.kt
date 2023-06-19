package com.amatkovsky.albums.feed.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amatkovsky.albums.feed.presentation.FeedState
import com.amatkovsky.albums.feed.presentation.FeedViewModel
import com.amatkovsky.albums.feed.presentation.LoadMoreAction
import com.amatkovsky.albums.feed.presentation.PaginationState

@Composable
fun Feed(
    viewModel: FeedViewModel = hiltViewModel(),
) {
    val lazyColumnListState = rememberLazyListState()
    val state by viewModel.observeState().collectAsStateWithLifecycle(initialValue = FeedState.Loading)

    val loadNext = remember {
        derivedStateOf {
            state.paginationState == PaginationState.IDLE
                    && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -100) >= lazyColumnListState.layoutInfo.totalItemsCount - 10
        }
    }

    when (val s = state) {
        FeedState.Loading    -> Text(text = "Loading")
        is FeedState.Success -> {
            LazyColumn(state = lazyColumnListState) {
                items(s.photos) { album ->
                    Text(text = album)
                }
            }
        }
    }

    LaunchedEffect(key1 = loadNext.value) {
        viewModel.dispatch(LoadMoreAction)
    }

}

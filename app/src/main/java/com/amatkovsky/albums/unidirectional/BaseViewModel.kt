package com.amatkovsky.albums.unidirectional

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseViewModel<S>(initialState: S, effects: List<Effect>) : ViewModel() {

    private val store = MutableStateFlow(initialState)

    private val actions = MutableSharedFlow<Action>(extraBufferCapacity = 1)

    init {
        effects.forEach { effect ->
            effect.observe(actions)
                .onEach { action -> actions.emit(action) }
                .launchIn(viewModelScope)
        }

        dispatch(Initial)
    }

    fun observeState(): Flow<S> = store

    fun dispatch(action: Action) {
        actions.tryEmit(action)
    }
}

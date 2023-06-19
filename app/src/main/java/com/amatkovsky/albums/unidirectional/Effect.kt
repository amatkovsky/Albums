package com.amatkovsky.albums.unidirectional

import kotlinx.coroutines.flow.Flow

interface Effect<S> {

    fun observe(actions: Flow<Action>, state: () -> S): Flow<Action>

}

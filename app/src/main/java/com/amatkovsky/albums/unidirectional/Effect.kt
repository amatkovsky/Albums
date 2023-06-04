package com.amatkovsky.albums.unidirectional

import kotlinx.coroutines.flow.Flow

interface Effect {
    fun observe(actions: Flow<Action>): Flow<Action>
}

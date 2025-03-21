package com.asap.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

object ExpiredTokenHandler{
    private val _expiredTokenFlow = MutableSharedFlow<Unit>()

    fun subscribe(scope: CoroutineScope, block: () -> Unit) {
        scope.launch {
            _expiredTokenFlow.collect {
                block()
            }
        }
    }

    fun emit(scope: CoroutineScope) {
        scope.launch {
            _expiredTokenFlow.emit(Unit)
        }
    }
}
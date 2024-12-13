package com.asap.aljyo.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.IntOffset

fun defaultEnterTransition(): EnterTransition {
    return slideIn(
        animationSpec = tween(durationMillis = 200, delayMillis = 100),
        initialOffset = { size -> IntOffset(0, size.height / 4) }
    ) + fadeIn(animationSpec = tween(durationMillis = 200, delayMillis = 100))
}

fun defaultExitTransition(): ExitTransition {
    return slideOut(
        animationSpec = tween(durationMillis = 200, delayMillis = 100),
        targetOffset = { size -> IntOffset(0, size.height / 4) }
    ) + fadeOut(animationSpec = tween(durationMillis = 200, delayMillis = 100))
}
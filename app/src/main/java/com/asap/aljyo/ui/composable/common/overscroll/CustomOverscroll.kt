package com.asap.aljyo.ui.composable.common.overscroll

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign

object CustomOverscroll {
    private fun Float.valid(): Boolean = abs(this) > 0.5f

    private fun sameDirection(first: Float, second: Float): Boolean {
        return sign(first) == sign(second)
    }

    @OptIn(ExperimentalFoundationApi::class)
    class HorizontalClamping(
        private val scope: CoroutineScope,
        private val resistance: Float = 0.2f,
    ) : OverscrollEffect {
        private val overscroll by mutableStateOf(Animatable(0f))

        override fun applyToScroll(
            delta: Offset,
            source: NestedScrollSource,
            performScroll: (Offset) -> Offset
        ): Offset {
            val deltaX = delta.x
            val sameDirection = sameDirection(deltaX, overscroll.value)

            val undoOverscroll = if (overscroll.value.valid() && !sameDirection) {
                val oldOverscroll = overscroll.value
                val newOverscroll = overscroll.value + deltaX

                // overscroll 끝나는 시점
                if (!sameDirection(oldOverscroll, newOverscroll)) {
                    scope.launch {
                        overscroll.snapTo(0f)
                    }
                    deltaX + oldOverscroll
                } else {
                    scope.launch {
                        overscroll.snapTo(overscroll.value + deltaX * resistance)
                    }
                    deltaX
                }

            } else {
                0f
            }

            val adjustedDelta = deltaX - undoOverscroll
            val scrolledDelta = performScroll(Offset(adjustedDelta, 0f)).x
            val overscrollDelta = adjustedDelta - scrolledDelta

            if (overscrollDelta.valid() && source == NestedScrollSource.UserInput) {
                scope.launch {
                    overscroll.snapTo(overscroll.value + overscrollDelta * resistance)
                }
            }

            return Offset(undoOverscroll + scrolledDelta, 0f)
        }

        override suspend fun applyToFling(
            velocity: Velocity,
            performFling: suspend (Velocity) -> Velocity
        ) {
            val consumed = performFling(velocity)
            val remaining = velocity - consumed

            overscroll.animateTo(
                targetValue = 0f,
                initialVelocity = remaining.x,
                animationSpec = tween(durationMillis = 200, easing = EaseOutQuad)
            )
        }

        override val isInProgress: Boolean
            get() = overscroll.value != 0f

        override val effectModifier: Modifier
            get() = Modifier.offset { IntOffset(x = overscroll.value.roundToInt(), y = 0) }

    }
}
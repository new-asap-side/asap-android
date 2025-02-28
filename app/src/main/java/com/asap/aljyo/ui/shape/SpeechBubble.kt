package com.asap.aljyo.ui.shape

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

sealed interface TailArrangement {
    fun arrange(width: Float): Float

    data object Start : TailArrangement {
        override fun arrange(width: Float): Float = width * 0.2f
    }

    data object Center : TailArrangement {
        override fun arrange(width: Float): Float = width * 0.5f
    }

    data object End : TailArrangement {
        override fun arrange(width: Float): Float = width * 0.8f
    }
}

// 말풍선 모양
class SpeechBubble(
    private val bodyRadius: Float,
    private val tailSize: Float,
    private val arrangement: TailArrangement = TailArrangement.Center
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Generic(
        drawSpeechBubble(
            size = size,
            bodyRadius = bodyRadius,
            tailSize = tailSize,
            tailCenter = arrangement.arrange(size.width)
        )
    )
}

private fun drawSpeechBubble(
    size: Size,
    bodyRadius: Float,
    tailSize: Float,
    tailCenter: Float,
): Path {
    return Path().apply {
        reset()
        RoundRect(
            Rect(0f, 0f, size.width, size.height - tailSize),
            CornerRadius(bodyRadius)
        ).run {
            addRoundRect(this)
        }

        moveTo(tailCenter - tailSize / 2, size.height - tailSize)
        lineTo(tailCenter, size.height)
        lineTo(tailCenter + tailSize / 2, size.height - tailSize)

        close()
    }
}
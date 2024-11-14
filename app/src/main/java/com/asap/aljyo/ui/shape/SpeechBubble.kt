package com.asap.aljyo.ui.shape

import androidx.compose.ui.geometry.Rect
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

    data object Center: TailArrangement {
        override fun arrange(width: Float): Float = width * 0.5f
    }

    data object End: TailArrangement {
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
            tailStartPosition = arrangement.arrange(size.width)
        )
    )
}

private fun drawSpeechBubble(
    size: Size,
    bodyRadius: Float,
    tailSize: Float,
    tailStartPosition: Float,
): Path {
    return Path().apply {
        reset()

        arcTo(
            rect = Rect(
                left = 0f,
                top = 0f,
                right = bodyRadius,
                bottom = bodyRadius
            ),
            startAngleDegrees = -180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        lineTo(x = size.width - bodyRadius, y = 0f)

        arcTo(
            rect = Rect(
                left = size.width - bodyRadius,
                top = 0f,
                right = size.width,
                bottom = bodyRadius
            ),
            startAngleDegrees = -90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        lineTo(x = size.width, y = size.height - bodyRadius - tailSize)

        arcTo(
            rect = Rect(
                left = size.width - bodyRadius,
                top = size.height - bodyRadius - tailSize,
                right = size.width,
                bottom = size.height - tailSize
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        // 말풍선 꼬리
        lineTo(x = tailStartPosition + tailSize, y = size.height - tailSize)

        lineTo(x = tailStartPosition + tailSize / 2f, y = size.height)

        lineTo(x = tailStartPosition, y = size.height - tailSize)

        lineTo(x = bodyRadius, y = size.height - tailSize)

        arcTo(
            rect = Rect(
                left = 0f,
                top = size.height - tailSize - bodyRadius,
                right = bodyRadius,
                bottom = size.height - tailSize
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        close()
    }
}
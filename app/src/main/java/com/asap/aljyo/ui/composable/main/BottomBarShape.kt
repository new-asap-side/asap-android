package com.asap.aljyo.ui.composable.main

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection


class BottomBarShape(
    private val marginPx: Float,
    private val radiusPx: Float,
    private val gap: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        moveTo(0f, marginPx + radiusPx)
        arcTo(
            rect = Rect(
                left = 0f,
                top = marginPx,
                right = radiusPx,
                bottom = marginPx + radiusPx
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false,
        )
        lineTo((size.width / 2) - gap, marginPx)
        quadraticTo(
            x1 = (size.width / 2),
            y1 = -marginPx,
            x2 = (size.width / 2) + gap,
            y2 = marginPx
        )
        lineTo(size.width - radiusPx, marginPx)
        arcTo(
            rect = Rect(
                left = size.width - radiusPx,
                top = marginPx,
                right = size.width,
                bottom = marginPx + radiusPx
            ),
            startAngleDegrees = -90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
        lineTo(0f, marginPx + radiusPx)
    })
}
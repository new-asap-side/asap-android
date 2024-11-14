package com.asap.aljyo.ui.composable.common.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
inline fun OverlappingRow(
    modifier: Modifier = Modifier,
    overlapFactor: Float = 0.5f,
    horizontlaArrangement: Arrangement.Horizontal = Arrangement.Start,
    content: @Composable () -> Unit,
) {
    val measurePolicy = rememberOverlappingRowMeasurePolicy(
        overlapFactor = overlapFactor,
        horizontalArrangement = horizontlaArrangement
    )
    Layout(
        modifier = modifier,
        measurePolicy = measurePolicy,
        content = content
    )
}

@PublishedApi
@Composable
internal fun rememberOverlappingRowMeasurePolicy(
    overlapFactor: Float,
    horizontalArrangement: Arrangement.Horizontal,
): MeasurePolicy {
    return remember(overlapFactor, horizontalArrangement) {
         overlappingMeasurePolicy(
            overlapFactor,
            arrangement = { totalSize, sizes, layoutDirection, density, outPosition ->
                with(horizontalArrangement) {
                    density.arrange(totalSize, sizes, layoutDirection, outPosition)
                }
            }
        )
    }
}

internal fun overlappingMeasurePolicy(
    overlapFactor: Float,
    arrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit
): MeasurePolicy {
    return MeasurePolicy { measurables, constraints ->
        val placeables = arrayOfNulls<Placeable>(measurables.size)

        var measuredWidth = 0
        var measuredHeight = 0

        for (i in measurables.indices) {
            // 하위 컴포저블에 constraints 정의
            val measurable = measurables[i]
            val placeable = measurable.measure(
                Constraints(
                    minWidth = 0,
                    maxWidth = if (constraints.maxWidth == Constraints.Infinity) {
                        Constraints.Infinity
                    } else {
                        (constraints.maxWidth - measuredWidth)
                    },
                    minHeight = 0,
                    maxHeight = constraints.maxHeight
                )
            )

            // measure된 컴포저블의 크기를 기반으로
            // 배치할 컴포저블 크기 정보 갱신
            placeables[i] = placeable
            measuredWidth += if (i == measurables.lastIndex) {
                placeable.width
            } else {
                (placeable.width * overlapFactor).roundToInt()
            }
            measuredHeight = max(measuredHeight, placeable.height)
        }

        val layoutWidth = max(measuredWidth, constraints.minWidth)
        val layoutHeight = measuredHeight

        val childrenWidthSizes = placeables.mapIndexed { index, placeable ->
            if (index == placeables.size)
                placeable!!.width
            else
                (placeable!!.width * overlapFactor).roundToInt()
        }.toIntArray()

        val placeablePosition = IntArray(placeables.size) { 0 }
        arrangement(
            layoutWidth,
            childrenWidthSizes,
            this.layoutDirection,
            this,
            placeablePosition
        )

        layout(layoutWidth, layoutHeight) {
            placeables.mapIndexed { index, placeable ->
                val position = placeablePosition[index]
                placeable!!.place(position, 0 )
            }
        }
    }
}

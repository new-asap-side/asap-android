package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.shape.SpeechBubble
import com.asap.aljyo.ui.shape.TailArrangement
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White
import kotlin.math.roundToInt

@Composable
@NonRestartableComposable
internal fun RankingPopup(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    offset: IntOffset,
) {
    Popup(
        onDismissRequest = onDismiss,
        popupPositionProvider = object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset = offset
        }
    ) {
        val tailHeight = LocalDensity.current.run {
            10.dp.toPx()
        }
        Box(
            modifier = modifier
                .height(50.dp)
                .clip(
                    SpeechBubble(
                        bodyRadius = 100f,
                        tailSize = tailHeight,
                        arrangement = TailArrangement.End
                    )
                )
                .background(Black01)
        ) {
            Row(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(
                            constraints.copy(
                                maxHeight = constraints.maxHeight - tailHeight.roundToInt()
                            )
                        )
                        layout(placeable.width, placeable.height) {
                            placeable.place(0, 0)
                        }
                    }
                    .fillMaxHeight()
                    .padding(
                        start = 46.dp,
                        end = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.confirm_rank),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 14.fsp,
                        color = White
                    )
                )
            }
        }
        Image(
            modifier = Modifier
                .size(33.dp)
                .offset(x = 10.dp, y = (-5).dp),
            painter = painterResource(R.drawable.img_gold_medal),
            contentDescription = "Gold medal image"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmRankingDialogPreview() {
    AljyoTheme {
        RankingPopup(
            onDismiss = {},
            offset = IntOffset.Zero
        )
    }
}
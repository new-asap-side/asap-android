package com.asap.aljyo.ui.composable.release_alarm

import com.asap.aljyo.R

sealed class AlarmContent(
    val titleId: Int,
    val descriptionId: Int
) {
    data object Slide: AlarmContent(
        titleId = R.string.drag_to_release_alarm,
        descriptionId = R.string.drag_description
    )
    data object Card: AlarmContent(
        titleId = R.string.touch_to_release_alarm,
        descriptionId = R.string.select_card_description
    )
}
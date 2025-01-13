package com.asap.aljyo.ui.composable.group_ranking

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun MeBadge(
    modifier: Modifier = Modifier,
    style: TextStyle? = null
) {
    Text(
        modifier = modifier,
        text = "ME!",
        style = style ?: MaterialTheme.typography.labelMedium.copy(
            color = MaterialTheme.colorScheme.primary
        )
    )
}
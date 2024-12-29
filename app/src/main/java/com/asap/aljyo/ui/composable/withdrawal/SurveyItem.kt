package com.asap.aljyo.ui.composable.withdrawal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.custom.AljyoToggleButton
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01

@Composable
internal fun SurveyItem(
    modifier: Modifier = Modifier,
    survey: String,
    selected: Boolean,
    onSelect: (Boolean) -> Unit,
    inputField: (@Composable () -> Unit)? = null
) {
    Column {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AljyoToggleButton(
                selected = selected,
                onSelect = onSelect
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = survey,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Black01,
                    fontSize = 15.fsp
                )
            )
        }

        if (inputField != null) {
            inputField()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        SurveyItem(
            survey = "설문 문항",
            selected = false,
            onSelect = {}
        )
    }
}
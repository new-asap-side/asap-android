package com.asap.aljyo.ui.composable.main.alarm_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01

@Composable
internal fun AlarmTimer(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            text = buildAnnotatedString {
                append(text = "${stringResource(R.string.sir, "Hi")},\n")
                withStyle(
                    style = SpanStyle(
                        fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                    )
                ) {
                    append(
                        text = "${
                            stringResource(
                                R.string.after_time, "12", "09"
                            )
                        }\n"
                    )
                }
                append(text = stringResource(R.string.the_alarm_goes_off))
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 20.sp,
                color = Black01,
                lineHeight = 28.sp,
            )
        )
        Image(
            painter = painterResource(R.drawable.img_alarm_list_illust),
            contentDescription = "Alarm list illust"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        AlarmTimer(
            modifier = Modifier.fillMaxWidth()
        )
    }
}
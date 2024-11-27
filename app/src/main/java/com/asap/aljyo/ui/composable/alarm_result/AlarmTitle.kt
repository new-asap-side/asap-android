package com.asap.aljyo.ui.composable.alarm_result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.White

@Composable
internal fun AlarmTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(R.drawable.ic_clock),
            contentDescription = "Clcok icon",
            tint = White
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = stringResource(R.string.alarm_title),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 14.sp,
                    color = Grey01
                )
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.sp,
                    color = White
                )
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        AlarmTitle(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16))
                .background(Color(0xFFDB7797).copy(alpha = 0.5f))
                .padding(horizontal = 20.dp, vertical = 16.dp),
            title = "Title"
        )
    }
}
package com.asap.aljyo.ui.composable.main.alarm_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.White

@Composable
internal fun EmptyAlarmList(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Icon(
            modifier = Modifier.size(width = 90.dp, height = 86.dp),
            painter = painterResource(R.drawable.ic_empty_alarm),
            contentDescription = "Empty alarm icon",
            tint = Color.Unspecified
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringResource(R.string.empty_alarm),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 22.sp,
                    color = Black01,
                )
            )

            Text(
                text = stringResource(R.string.start_with_new_group),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 15.sp,
                    color = Black03,
                )
            )
        }

        TextButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = Black01,
                contentColor = White
            ),
            onClick = navigateToHome
        ) {
            Text(
                text = stringResource(R.string.find_group),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 15.sp,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        EmptyAlarmList()
    }
}
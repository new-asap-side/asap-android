package com.asap.aljyo.ui.composable.common.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.White

@Composable
internal fun LeaveGroupDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier.size(300.dp, 238.dp)
        ) {
            Column(
                modifier = Modifier
                    .offset(y = 32.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(White)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(42.dp))
                Text(
                    text = stringResource(R.string.ask_leave_group),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 18.sp,
                        color = Black01
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.ranking_initialized),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 14.sp,
                        color = Black02
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                        onClick = { onDismissRequest() }
                    ) {
                        Text(
                            text = stringResource(R.string.no)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            // 그룹 탈퇴 요청
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.yes)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier.width(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(78.dp),
                painter = painterResource(R.drawable.img_exclamation_mark),
                contentDescription = "Exclamation mark",
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        LeaveGroupDialog { }
    }
}
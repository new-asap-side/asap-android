package com.asap.aljyo.ui.composable.group_form.group_create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.White

const val GROUP_TITLE = 0
const val GROUP_DESCRIPTION = 1

@Composable
fun GroupInputField(
    modifier: Modifier = Modifier,
    type: Int,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: @Composable () -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = 8.dp, top = 28.dp),
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Black02,
                fontSize = 14.sp
            )
        )
        Box {
            OutlinedTextField(
                modifier = modifier,
                value = value,
                onValueChange = onValueChange,
                singleLine = false,
                maxLines = Int.MAX_VALUE,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Start,
                    fontSize = 15.sp,
                    color = Black01
                ),
                placeholder = placeHolder,
                shape = RoundedCornerShape(6.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedIndicatorColor = Black01,
                    unfocusedIndicatorColor = Grey02,
                    cursorColor = Red01
                ),
            )
            if (type == GROUP_DESCRIPTION) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 12.dp),
                    text = "${value.length} / 50",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Black04,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}
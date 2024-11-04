package com.asap.aljyo.ui.composable.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.components.usersetting.UserSettingMsgType
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.Error
import com.asap.aljyo.ui.theme.Red01
import kotlinx.coroutines.delay

@Composable
fun NicknameTextField(
    modifier: Modifier = Modifier,
    nickname: String,
    onNicknameChange: (String) -> Unit,
    onFocusChange: () -> Unit,
    onNicknameCheck: (String) -> Unit,
    msg: UserSettingMsgType = UserSettingMsgType.None,
) {
    var isFirstFocus by remember { mutableStateOf(false)}

    LaunchedEffect(nickname) {
        delay(500)
        if (msg != UserSettingMsgType.LengthError && isFirstFocus) {
            onNicknameCheck(nickname)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        BasicTextField(
            value = nickname,
            onValueChange = { newText ->
                if(isNicknameValid(newText)) {
                    onNicknameChange(newText)
                }
            } ,
            Modifier.onFocusChanged {
                if (it.isFocused && !isFirstFocus) {
                    isFirstFocus = true
                    onFocusChange()
                }
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = Black01,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            ),
            cursorBrush = SolidColor(Red01),
            singleLine = true,
            maxLines = 1,
            decorationBox = { innerTextField ->
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(26.dp)
                ) {
                    if (nickname.isEmpty()) {
                        Text(
                            text = "닉네임",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Black04,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                            ),
                            modifier = modifier.align(Alignment.CenterStart)
                        )
                    }
                    Box(
                        modifier = modifier
                            .align(Alignment.CenterStart)
                    ) {
                        innerTextField()
                    }
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    ) {
                        Text(
                            text = "${nickname.length}/8",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Black04,
                                fontSize = 15.sp
                            ),
                            modifier = Modifier
                                .padding(end = 10.dp)
                        )
                        if (nickname.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Text",
                                modifier = modifier
                                    .size(20.dp)
                                    .clickable {
                                        onNicknameChange("")
                                    }
                            )
                        }
                    }
                }
            }
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .height(2.dp)
                .background(if (msg != UserSettingMsgType.None) Color.Red else Color.Gray)
        )

        if (msg != UserSettingMsgType.None) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = msg.msg,
                style = TextStyle(
                    color = if (msg == UserSettingMsgType.Success) Black02 else Error,
                    fontSize = 12.sp
                )
            )
        }
    }
}

private fun isNicknameValid(nickname: String): Boolean {
    val nicknameRegex =  "^[가-힣ㄱ-ㅎㅏ-ㅣ]{0,8}$".toRegex()
    return !nickname.contains("\\s".toRegex()) // 공백을 포함하지 않도록
            && nicknameRegex.matches(nickname)
}

@Composable
@Preview
fun PreviewNickNameEditText() {
    NicknameTextField(
        nickname = "",
        onNicknameChange = {

        },
        msg = UserSettingMsgType.None,
        onFocusChange = {},
        onNicknameCheck =  {}
    )
}
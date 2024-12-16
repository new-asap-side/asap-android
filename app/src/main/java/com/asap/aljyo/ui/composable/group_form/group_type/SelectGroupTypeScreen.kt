package com.asap.aljyo.ui.composable.group_form.group_type

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.composable.group_form.GroupProgressbar
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.Red02

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectGroupTypeScreen(
    navigateToCreateGroup: (Boolean, String?) -> Unit,
    onBackClick: () -> Unit
) {
    var isSelected by remember { mutableIntStateOf(-1) }
    var password by remember { mutableStateOf("") }
    val buttonState by remember {
        derivedStateOf {
            (isSelected == 0) || (isSelected == 1 && password.length == 4)
        }
    }

    Scaffold(
        containerColor = White,
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_top_back),
                            contentDescription = "BACK"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            GroupProgressbar(
                startProgress = 0.0f,
                endProgress = 0.25f
            )
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 16.dp),
                text = "어떤 알람을 만드시겠어요?",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Text(
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = 20.dp
                ),
                text = "그룹 생성 후에도 변경이 가능해요!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontSize = 15.sp,
                )
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            BoxWithCheckButton(
                R.drawable.ic_public,
                "공개",
                isSelected = isSelected == 0,
                onCheckedChange = { isSelected = 0 }
            )
            Spacer(modifier = Modifier.height(4.dp))
            BoxWithCheckButton(
                R.drawable.ic_private,
                "비공개",
                isSelected = isSelected == 1,
                onCheckedChange = { isSelected = 1 }
            )
            Spacer(modifier = Modifier.height(30.dp))
            if (isSelected == 1) {
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = "비밀번호",
                    fontSize = 12.sp,
                    color = Black03
                )
                Spacer(modifier = Modifier.height(6.dp))
                UnderlineTextField(
                    value = password,
                    onValueChange = { newText ->
                        if(newText.length <= 4) password = newText
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .imePadding(),
                text = "확인",
                enable = buttonState,
                onClick = {
                    if (isSelected == 0) {
                        navigateToCreateGroup(true, null)
                    } else {
                        navigateToCreateGroup(false, password)
                    }
                }
            )
        }
    }
}

@Composable
fun BoxWithCheckButton(
    @DrawableRes icon: Int,
    text: String,
    isSelected: Boolean,
    onCheckedChange: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                end = 20.dp
            )
            .border(
                width = 2.dp,
                color = if (isSelected) Red01 else Grey02,
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                color = if (isSelected) Red02 else White
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Red01),
                onClick = onCheckedChange
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(
                    top = 20.dp,
                    bottom = 20.dp,
                    start = 20.dp
                ),
                painter = painterResource(icon),
                contentDescription = "IconPublic",
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                text = text,
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = if (isSelected) Red01 else Black01,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.padding(end = 20.dp),
                painter = painterResource(
                    if (isSelected) R.drawable.ic_check_true else R.drawable.ic_check_false
                ),
                contentDescription = "Check Icon",
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun UnderlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = Black01,
            fontSize = 18.sp
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    ) { innerTextField ->
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp
                )
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.CenterStart // 위아래 중앙 정렬 설정
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = "비밀번호를 입력해주세요(숫자 4자리)",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Black04,
                            fontSize = 18.sp
                        ),
                    )
                }
                innerTextField()
            }
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .height(2.dp)
                    .background(color = Black02, shape = RoundedCornerShape(3.dp))
            )
        }
    }
}

@Composable
@Preview
fun PreviewBoxWithCheckButton() {

}

@Composable
@Preview
fun PreviewSelectGroupTypeScreen() {
//    AljyoTheme {
//        SelectGroupTypeScreen(
//            navigateToCreateGroup = {},
//            onBackClick = {}
//        )
//    }
}
package com.asap.aljyo.ui.composable.group_form.group_alarm

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02

@Composable
fun CustomAlertDialog(
    title: String,
    content: String,
    onClick: () -> Unit,
    @DrawableRes dialogImg: Int? = null
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(1.0f)
                .padding(top = 44.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 65.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Black01,
                        fontSize = 18.fsp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = content,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Black02,
                        fontSize = 14.fsp,
                    )
                )

                CustomButton(
                    modifier = Modifier.padding(vertical = 20.dp),
                    text = "확인",
                    enable = true,
                    onClick = onClick
                )
            }
            dialogImg?.let {
                Image(
                    modifier = Modifier
                        .absoluteOffset(y = (-44).dp),
                    painter = painterResource(it),
                    contentDescription = "Group Dialog Image"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingDialogPreview() {
    AljyoTheme  {
        CustomAlertDialog(
            title = "그룹 생성 완료!",
            content = "6시간 후부터 알람이 울려요",
            onClick =  {},
            dialogImg = R.drawable.group_dialog_img
        )
    }
}
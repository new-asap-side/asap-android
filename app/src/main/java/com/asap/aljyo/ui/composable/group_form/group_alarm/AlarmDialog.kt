package com.asap.aljyo.ui.composable.group_form.group_alarm

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.composable.common.dialog.DialogButtonType
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.White

@Composable
fun CustomAlertDialog(
    buttonType: DialogButtonType = DialogButtonType.SINGLE,
    title: String,
    content: String,
    onClickConfirm: () -> Unit,
    confirmText: String = "",
    onDismissRequest: () -> Unit = {},
    dismissText: String = "",
    @DrawableRes dialogImg: Int? = null
) {
    AljyoTheme {
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

                    when (buttonType) {
                        DialogButtonType.SINGLE -> {
                            Button(
                                modifier = Modifier
                                    .padding(vertical = 20.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                onClick = onClickConfirm
                            ) {
                                Text(
                                    text = confirmText,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 16.fsp,
                                        color = Color.White
                                    )
                                )
                            }
                        }

                        DialogButtonType.DOUBLE -> {
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 20.dp)
                                    .fillMaxWidth(),
                            ) {
                                Button(
                                    modifier = Modifier.weight(1f),
                                    border = BorderStroke(1.dp, Red01),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = White
                                    ),
                                    onClick = onDismissRequest
                                ) {
                                    Text(
                                        text = dismissText,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontSize = 16.fsp,
                                            color = Red01
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = onClickConfirm
                                ) {
                                    Text(
                                        text = confirmText,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontSize = 16.fsp,
                                            color = White
                                        )
                                    )
                                }
                            }
                        }
                    }
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
}

@Preview(showBackground = true)
@Composable
fun LoadingDialogPreview() {
    AljyoTheme {
        CustomAlertDialog(
            buttonType = DialogButtonType.DOUBLE,
            title = "새로운 아이템이 해제되었어요!",
            content = "마이페이지에서 확인하세요",
            onClickConfirm = {},
            onDismissRequest = {},
            confirmText = "확인하러 가기",
            dismissText = "닫기"
        )
    }
}
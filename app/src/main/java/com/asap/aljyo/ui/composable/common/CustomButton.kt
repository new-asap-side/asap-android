package com.asap.aljyo.ui.composable.common

import android.graphics.Color
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.White

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    enable: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Red01,
            contentColor = White,
            disabledContainerColor = Grey02,
            disabledContentColor = Black04
        ),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        enabled = enable
    ) {
        Text(
            modifier = Modifier.padding(top = 14.dp, bottom = 14.dp),
            text = text,
            style = TextStyle.Default.copy(
                fontSize = 16.sp
            )
        )
    }
}
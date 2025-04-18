package com.asap.aljyo.ui.composable.group_form.group_create

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Grey02

@Composable
fun MemberPicker(
    value: Int,
    onPlusClick: (Int) -> Unit,
    onMinusClick:(Int) -> Unit
) {
    val currentPerson by remember { mutableIntStateOf(value) }

    Column {
        Text(
            modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
            text = "그룹 인원",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Black02,
                fontSize = 15.fsp
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Grey02,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "인원",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.fsp,
                        fontWeight = FontWeight.Bold,
                        color = Black01
                    )
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "최대 10명",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.fsp,
                        color = Black03
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    modifier = Modifier.size(16.dp),
                    onClick = { onMinusClick(value - 1) },
                    enabled = value > currentPerson
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_minus),
                        contentDescription = "Member Picker Minus"
                    )
                }

                Text(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    text = value.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.fsp,
                        fontWeight = FontWeight.Bold,
                        color = Black01
                    )
                )

                IconButton(
                    modifier = Modifier.size(16.dp),
                    onClick = { onPlusClick(value + 1) },
                    enabled = value < 10
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = "Member Picker Plus"
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewMemberPicker() {
    var member by remember { mutableIntStateOf(1) }
    AljyoTheme() {
        MemberPicker(
            value = member,
            onPlusClick = { member++ },
            onMinusClick = { member-- }
        )
    }
}

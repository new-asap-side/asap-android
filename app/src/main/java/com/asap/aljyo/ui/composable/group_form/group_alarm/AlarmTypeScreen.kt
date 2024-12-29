package com.asap.aljyo.ui.composable.group_form.group_alarm

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.asap.aljyo.R
import com.asap.aljyo.core.components.group_form.GroupFormViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.composable.group_form.GroupProgressbar
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.Red02

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmTypeScreen(
    viewModel: GroupFormViewModel,
    onBackClick: () -> Unit,
    navigateToAlarmSetting: () -> Unit
) {
    val alarmState by viewModel.alarmScreenState.collectAsStateWithLifecycle()

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
        GroupProgressbar(
            modifier = Modifier.padding(innerPadding),
            startProgress = 0.5f,
            endProgress = 0.75f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp, top = 16.dp)
        ) {
            Text(
                text = "\"닉네임\"님만의 알람 방식을\n선택해주세요!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Black,
                    fontSize = 22.fsp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Spacer(modifier = Modifier.height(90.dp))

            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.alarm_bell),
                contentDescription = "Alarm Bell Image"
            )

            Spacer(modifier = Modifier.weight(1f))

            BoxWithIcon(
                icon = R.drawable.ic_slide,
                text = "밀어서 알람 해제",
                isSelected = alarmState.alarmUnlockContents == "SLIDE",
                onCheckedChange = { viewModel.onAlarmUnlockContentsSelected("SLIDE") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            BoxWithIcon(
                icon = R.drawable.ic_card_touch,
                text = "카드를 터치하여 알람 해제",
                isSelected = alarmState.alarmUnlockContents == "CARD",
                onCheckedChange = { viewModel.onAlarmUnlockContentsSelected("CARD") }
            )

            Spacer(modifier = Modifier.height(36.dp))

            CustomButton(
                text = "다음",
                enable = alarmState.alarmUnlockContents.isNotEmpty(),
                onClick = navigateToAlarmSetting
            )

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
fun BoxWithIcon(
    @DrawableRes icon: Int,
    text: String,
    isSelected: Boolean,
    onCheckedChange: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
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
            modifier = Modifier.fillMaxWidth(),
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
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                text = text,
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = if (isSelected) Red01 else Black01,
                    fontSize = 16.fsp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}
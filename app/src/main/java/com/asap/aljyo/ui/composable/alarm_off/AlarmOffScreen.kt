package com.asap.aljyo.ui.composable.alarm_off

import android.graphics.Color
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.service.AlarmService
import com.asap.aljyo.core.components.viewmodel.AlarmOffViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.alarm.AlarmPayload
import com.asap.domain.entity.remote.alarm.AlarmUnlockContent
import androidx.compose.ui.graphics.Color as compose

private val gradient = listOf(
    compose(0xFFFFDF8F),
    compose(0xFFFF999E),
    compose(0xFFFF5FA9),
    compose(0xFFFF5FA9),
)

@Composable
fun AlarmOffScreen(
    alarm: AlarmPayload,
    navigateToResult: (Int) -> Unit,
    viewModel: AlarmOffViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val requestState by viewModel.state.collectAsState()

    LaunchedEffect(requestState) {
        when (requestState) {
            RequestState.Initial, RequestState.Loading -> Unit

            is RequestState.Success -> {
                val result = (requestState as RequestState.Success).data
                if (result) {
                    // 알람 서비스 종료
                    AlarmService.stopAlarmService(context)
                    navigateToResult(0)
                }
            }

            is RequestState.Error -> {
                Toast.makeText(context, "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                AlarmService.stopAlarmService(context = context.applicationContext)
            }
        }
    }

    SideEffect {
        val activity = context as ComponentActivity
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )

        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    AljyoTheme {
        Scaffold { paddingValues ->
            BackHandler {
                val activity = context as ComponentActivity
                activity.finish()
            }

            if (requestState is RequestState.Loading) {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(gradient))
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val (groupId, groupTitle) = alarm
                val time = viewModel.currentTime
                val alarmContent: AlarmContent = when (alarm.content) {
                    AlarmUnlockContent.Slide -> Slide(groupId, groupTitle)
                    AlarmUnlockContent.Card -> Card(groupId, groupTitle)
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = time,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 86.fsp,
                        color = White
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100f))
                        .background(White.copy(alpha = 0.2f))
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.ic_clock),
                        contentDescription = "clock",
                        tint = White
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = alarmContent.title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 15.fsp,
                            color = White
                        )
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = alarmContent.description,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 24.fsp,
                        color = White
                    )
                )

                alarmContent.Content(viewModel)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(gradient))
    )
}
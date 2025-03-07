package com.asap.aljyo.ui.composable.release_alarm

import android.graphics.Color
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.release_alarm.ReleaseAlarmViewModel
import com.asap.aljyo.core.components.service.AlarmService
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.alarm.AlarmPayload
import com.asap.domain.entity.remote.alarm.AlarmUnlockContent
import kotlin.random.Random
import androidx.compose.ui.graphics.Color as compose

private val colors = listOf(
    compose(0xFFFFDF8F),
    compose(0xFFFF999E),
    compose(0xFFFF5FA9),
    compose(0xFFFF5FA9),
)

private val illusts = listOf(
    R.drawable.img_illust_water_drop,
    R.drawable.img_illust_flower,
    R.drawable.img_illust_rainbow,
    R.drawable.img_illust_cloud,
)


@Composable
internal fun ReleaseAlarmScreen(
    alarm: AlarmPayload,
    navigateToResult: (Int) -> Unit,
    viewModel: ReleaseAlarmViewModel = hiltViewModel()
) {
    val index by rememberSaveable {
        mutableIntStateOf(Random.run { nextInt(illusts.size) })
    }
    val context = LocalContext.current
    val requestState by viewModel.rState.collectAsState()

    LaunchedEffect(requestState) {
        when (requestState) {
            RequestState.Initial, RequestState.Loading -> Unit

            is RequestState.Success -> {
                val result = (requestState as RequestState.Success).data
                if (result) {
                    // 알람 서비스 종료
                    AlarmService.stopAlarmService(context)
                    navigateToResult(index)
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(colors))
                    .padding(paddingValues)
            ) {
                val content: AlarmContent = when (alarm.content) {
                    AlarmUnlockContent.Card -> AlarmContent.Card
                    AlarmUnlockContent.Slide -> AlarmContent.Slide
                }

                Column(
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val currentTime = viewModel.currentTime

                    Text(
                        text = currentTime,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 86.fsp,
                            color = White
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    Text(
                        text = stringResource(content.descriptionId),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 24.fsp,
                            color = White
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(content.titleId),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 18.fsp,
                            color = White
                        )
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    when (content) {
                        AlarmContent.Slide ->
                            DragArea(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                resourceId = illusts[index],
                                onComplete = {
                                    viewModel.alarmOff(alarm.groupId)
                                }
                            )

                        AlarmContent.Card ->
                            SelectCardArea(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                resourceId = illusts[index],
                                onComplete = {
                                    viewModel.alarmOff(alarm.groupId)
                                }
                            )
                    }
                }

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
            .background(brush = Brush.linearGradient(colors))
    )
}
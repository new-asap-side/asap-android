package com.asap.aljyo.ui.composable.alarm_result

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.alarm_result.AlarmResultViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White

private val colors = listOf(
    Color(0xFFFFEDBE),
    Color(0xFFFFB3B6),
    Color(0xFFFF98C7),
    Color(0xFFFF98C7),
)

@Composable
internal fun AlarmResultScreen(
    groupId: Int,
    title: String,
    index: Int,
    navigateToHome: () -> Unit,
    navigateToRanking: () -> Unit,
    viewModel: AlarmResultViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(groupId) {
        viewModel.fetchRankingNumber((groupId))
    }

    SideEffect {
        val activity = (context as ComponentActivity)
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            )
        )

        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    AljyoTheme {
        Scaffold(
            bottomBar = {
                Row(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 6.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.fsp,
                    )

                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            containerColor = White
                        ),
                        onClick = { navigateToRanking() }
                    ) {
                        Text(
                            text = stringResource(R.string.ranking),
                            style = style
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = White,
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = { navigateToHome() }
                    ) {
                        Text(
                            text = stringResource(R.string.finish),
                            style = style
                        )
                    }
                }
            }
        ) { paddingValues ->
            BackHandler {
                (context as ComponentActivity).finish()
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(colors))
                    .padding(paddingValues)
            ) {
                val rankNumberState by viewModel.rankingNumber.collectAsState()
                val rankNumber = if (rankNumberState is UiState.Success) {
                    (rankNumberState as UiState.Success).data
                } else {
                    "-"
                }

                FortuneCard(
                    modifier = Modifier.fillMaxWidth(),
                    index = index,
                    rank = rankNumber
                )

                Spacer(modifier = Modifier.height(20.dp))

                AlarmTitle(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16))
                        .background(Color(0xFFDB7797).copy(alpha = 0.5f))
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    title = title
                )
            }
        }
    }
}
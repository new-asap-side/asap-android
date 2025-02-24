package com.asap.aljyo.ui.composable.alarm_off

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.components.service.AlarmService
import com.asap.aljyo.core.components.viewmodel.CalculatorViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.theme.White

data class Calculator(
    override val id: Int, override val title: String
) : AlarmContent(id, title, "계산기의 답을 맞추면\n알람 해제!"),
    AlarmOffViewModelProvider<CalculatorViewModel> by CalculatorViewModelProvider() {
    @Composable
    override fun Effect(navigateToResult: () -> Unit) {
        val viewModel = provide()
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
                        navigateToResult()
                    }
                }

                is RequestState.Error -> {
                    Toast.makeText(context, "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    @Composable
    override fun Title(title: String) {
        val viewModel = provide()
        val time = viewModel.currentTime

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
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.fsp,
                    color = White
                )
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 24.fsp,
                color = White
            ),
            textAlign = TextAlign.Center
        )

    }

    @Composable
    override fun Content() {

    }

}

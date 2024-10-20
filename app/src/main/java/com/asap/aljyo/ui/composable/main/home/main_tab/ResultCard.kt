package com.asap.aljyo.ui.composable.main.home.main_tab

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.components.main.home.HomeViewModel
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03

@Composable
fun ResultCard(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Log.d("ResultCard composable","UiState: $uiState")

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Black01,
                                fontFamily = MaterialTheme.typography.headlineMedium.fontFamily
                            )
                        ) {
                            append("알죠")
                        }
                        append("${stringResource(R.string.of)}\n")
                        append("${stringResource(R.string.release_alarm_succes_rate)}\n")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontFamily = MaterialTheme.typography.headlineMedium.fontFamily
                            )
                        ) {
                            append("${49.1}%")
                        }
                        append(stringResource(R.string.`is`))
                    },
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        color = Black01
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(R.string.start_lively_today),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Black03,
                        fontSize = 15.sp
                    )
                )
            }
            Image(
                painter = painterResource(R.drawable.img_result_card_thumbnail),
                contentDescription = "result card thumbnail"
            )
        }
        Box() {

        }
    }
}
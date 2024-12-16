package com.asap.aljyo.ui.composable.withdrawal

import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.withdrawal.WithdrawalViewModel
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WithdrawalScreen(
    onBackIconPressed: () -> Unit,
    viewModel: WithdrawalViewModel = hiltViewModel(),
    preview: Boolean = false
) {
    val context = LocalContext.current
    if (!preview) {
        SideEffect {
            val window = (context as ComponentActivity).window
            WindowCompat.setDecorFitsSystemWindows(window,true)
        }
    }

    AljyoTheme {
        Scaffold(
            containerColor = White,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = White
                    ),
                    title = {
                        Text(
                            text = stringResource(R.string.membership_withdrawal),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Black01,
                                fontSize = 16.sp
                            ),
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { onBackIconPressed() }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close icon"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        onClick = { onBackIconPressed() },
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.sp
                            )
                        )
                    }

                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        enabled = viewModel.checkedPrecautions,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = White,
                            disabledContainerColor = Grey02,
                        ),
                        onClick = {},
                    ) {
                        Text(
                            text = stringResource(R.string.withdrawal),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 32.dp
                    )
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buildAnnotatedString {
                        append("${stringResource(R.string.really)} ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append(stringResource(R.string.app_name))
                        }
                        append("${stringResource(R.string.from)}\n")
                        append(stringResource(R.string.are_you_leave))
                    },
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Black01,
                        fontSize = 20.sp,
                        lineHeight = 28.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Survey(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Grey01)
                        .padding(
                            horizontal = 20.dp,
                            vertical = 24.dp
                        )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row {
                    Text(
                        text = stringResource(R.string.wait),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp,
                        )
                    )

                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.ic_hand),
                        contentDescription = "Wait icon",
                        tint = Color.Unspecified
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = stringResource(R.string.withdrawal_information_title),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 16.sp,
                        )
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    stringArrayResource(R.array.withdrawal_informations).forEach { info ->
                        Row {
                            Text(
                                text = "\u2022",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = Black02,
                                    fontSize = 12.sp,
                                )
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = info,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = Black02,
                                    fontSize = 12.sp,
                                )
                            )

                        }
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                PrecautionsButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
                )

            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        WithdrawalScreen(
            onBackIconPressed = {},
            preview = true
        )
    }
}
package com.asap.aljyo.ui.composable.withdrawal_complete

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WithdrawalCompleteScreen(
    navigateToOnboarding: () -> Unit = {},
) {
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
                )
            }
        ) { paddingValues ->
            Column (modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.height(86.dp),
                        painter = painterResource(R.drawable.img_illust_withdrawal),
                        contentDescription = "Withdrawal illust",
                        contentScale = ContentScale.FillHeight
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = stringResource(R.string.complete_withdrawal),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Black01,
                            fontSize = 20.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.see_you),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Black02,
                            fontSize = 15.sp
                        )
                    )
                }

                TextButton(
                    modifier = Modifier
                        .padding(
                            horizontal = 20.dp,
                            vertical = 6.dp
                        )
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = White
                    ),
                    onClick = { navigateToOnboarding() }
                ) {
                    Text(
                        text = stringResource(R.string.bye),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp
                        )
                    )

                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    WithdrawalCompleteScreen()
}
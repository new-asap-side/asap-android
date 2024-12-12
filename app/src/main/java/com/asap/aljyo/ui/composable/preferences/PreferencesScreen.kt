package com.asap.aljyo.ui.composable.preferences

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PreferencesScreen(
    modifier: Modifier = Modifier,
    onBackIconPressed: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        containerColor = White,
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.wrapContentHeight(),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = White
                ),
                title = {
                    Text(
                        text = stringResource(R.string.preferences),
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
                            painter = painterResource(R.drawable.ic_top_back),
                            contentDescription = "Back icon"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        PreferencesScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}
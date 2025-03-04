@file:OptIn(ExperimentalMaterial3Api::class)

package com.asap.aljyo.ui.composable.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.main.MainViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White

@Composable
fun AljyoTopAppBar(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel
) {
    val selectedIndex by mainViewModel.selectedIndex.collectAsState()
    when (selectedIndex) {
        0 -> SearchTopAppBar(modifier)
        1 -> MyAlarmTopAppBar(modifier)
        2 -> MyPageTopAppBar(modifier)
        else -> Unit
    }
}

@Composable
private fun SearchTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Image(
                painter = painterResource(R.drawable.ic_aljo),
                contentDescription = "top bar icon",
                contentScale = ContentScale.FillHeight
            )
        },
        actions = {
            IconButton(
                // TODO search logic
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "top bar action[search]"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
    )
}

@Composable
private fun MyAlarmTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.alarm_list),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Black01,
                    fontSize = 16.fsp
                ),
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
    )
}

@Composable
private fun MyPageTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.my_page),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Black01,
                    fontSize = 16.fsp
                ),
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
    )

}

@Preview
@Composable
private fun AljyoTopBarPreview() {
    AljyoTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SearchTopAppBar()

            MyAlarmTopAppBar()
            
            MyPageTopAppBar()
        }
    }
}
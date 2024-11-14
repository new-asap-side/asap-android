package com.asap.aljyo.ui.composable.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    appNavController: NavHostController
) {
    AljyoTheme {
        Scaffold(
            containerColor = White,
            topBar = {
                TopAppBar(
                    windowInsets = WindowInsets(0.dp),
                    modifier = Modifier
                        .height(44.dp)
                        .background(White)
                        .padding(top = 8.dp, bottom = 8.dp, start = 20.dp),
                    title = {
                        Image(
                            painter = painterResource(R.drawable.ic_aljo),
                            contentDescription = "Title bar icon",
                            contentScale = ContentScale.FillHeight
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
                )
            },
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                color = White
            ) {
                HomeTabScreen(navController = appNavController)
            }
        }
    }
}
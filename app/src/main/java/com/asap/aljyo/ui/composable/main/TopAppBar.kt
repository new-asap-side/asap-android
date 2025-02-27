package com.asap.aljyo.ui.composable.main

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AljyoTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Image(
                painter = painterResource(R.drawable.ic_aljo),
                contentDescription = "Title bar icon",
                contentScale = ContentScale.FillHeight
            )
        },
        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "title search action icon"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
    )
}

@Preview
@Composable
private fun AljyoTopBarPreview() {
    AljyoTheme {
        AljyoTopAppBar()
    }
}
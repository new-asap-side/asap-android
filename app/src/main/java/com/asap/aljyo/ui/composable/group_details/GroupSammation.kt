package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme

@Composable
fun GroupSummation(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = "",
            contentDescription = "Alarm group thumbnail",
            contentScale = ContentScale.FillWidth,
            error = painterResource(R.drawable.ic_my_page)
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun GroupSummationPreview() {
    AljyoTheme {
        GroupSummation(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
        )
    }
}
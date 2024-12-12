package com.asap.aljyo.ui.composable.main.my_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.White

@Composable
internal fun MyPageScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Grey01)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(vertical = 10.dp),
            text = stringResource(R.string.my_page),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Black01,
                fontSize = 16.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(42.dp))

        MyPageProfile(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            nickname = "알죠"
        )

        Spacer(modifier = Modifier.height(28.dp))

    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        MyPageScreen()
    }
}
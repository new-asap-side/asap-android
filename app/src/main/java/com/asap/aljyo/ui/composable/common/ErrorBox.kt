package com.asap.aljyo.ui.composable.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White

@Composable
internal fun ErrorBox(
    modifier: Modifier = Modifier,
    retryFunc: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.load_fail),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.fsp,
                color = Black01,
                textAlign = TextAlign.Center
            )
        )
        
        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            colors = ButtonDefaults.textButtonColors(
                containerColor = Black01,
                contentColor = White,
            ),
            onClick = { retryFunc() }
        ) {
            Text(
                text = stringResource(R.string.retry),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.fsp,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview_ErrorBox() {
    AljyoTheme {
        ErrorBox(modifier = Modifier.size(320.dp)) {}
    }
}
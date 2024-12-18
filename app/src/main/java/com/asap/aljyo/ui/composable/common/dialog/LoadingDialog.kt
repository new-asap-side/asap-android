package com.asap.aljyo.ui.composable.common.dialog

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.asap.aljyo.ui.theme.AljyoTheme

@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest) {
        CircularProgressIndicator(
            modifier = modifier,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        LoadingDialog { }
    }
}
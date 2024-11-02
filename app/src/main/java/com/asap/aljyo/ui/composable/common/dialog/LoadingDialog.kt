package com.asap.aljyo.ui.composable.common.dialog

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.asap.aljyo.ui.theme.AljyoTheme

@Composable
fun LoadingDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingDialogPreview() {
    AljyoTheme {
        LoadingDialog { }
    }
}
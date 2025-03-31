package com.asap.aljyo.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext

private const val FCM_TOKEN_KEY = "FCM token"

@Composable
fun ClipBoardMaskingBox(target: String, content: @Composable () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .wrapContentSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        copyToClipboard(context, target)
                    }
                )
            }
    ) {
        content()
    }
}

private fun copyToClipboard(context: Context, content: String) {
    (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
        setPrimaryClip(
            ClipData.newPlainText(FCM_TOKEN_KEY, content)
        )
    }
}
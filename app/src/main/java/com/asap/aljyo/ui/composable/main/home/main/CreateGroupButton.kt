package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White

@Composable
fun CreateGroupButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        contentColor = White,
        containerColor = MaterialTheme.colorScheme.primary,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 5.dp,
        ),
        onClick = onClick
    ) {
        Icon(
            Icons.Default.Add,
            modifier = Modifier.size(35.dp),
            contentDescription = "create group icon"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewGroupButtonPreview() {
    AljyoTheme {
        CreateGroupButton {}
    }
}
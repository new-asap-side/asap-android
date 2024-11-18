package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White

@Composable
fun NewGroupButton() {
    FloatingActionButton(
        contentColor = White,
        containerColor = MaterialTheme.colorScheme.primary,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 5.dp
        ),
        onClick = {}
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_new_group),
            contentDescription = "New group icon"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewGroupButtonPreview() {
    AljyoTheme {
        NewGroupButton()
    }
}
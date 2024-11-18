package com.asap.aljyo.ui.composable.common.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    arrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    containerColor: Color = White,
    title: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        containerColor = containerColor,
        onDismissRequest = onDismissRequest,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 24.dp
            ),
            verticalArrangement = arrangement
        ) {
            title()
            content()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        BottomSheet(
            onDismissRequest = {},
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Title")
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "close"
                    )
                }
            }
        ) {
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "Edit group"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.edit_group)
                )
            }
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_setting),
                    contentDescription = "Edit setting"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.edit_private_setting)
                )
            }
        }
    }
}
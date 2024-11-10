package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03

sealed class AlarmDetailsTabItem(val titleId: Int) {
    data object Details : AlarmDetailsTabItem(titleId = R.string.details)
    data object Settings : AlarmDetailsTabItem(titleId = R.string.private_setting)
}

private val tabItems = listOf(
    AlarmDetailsTabItem.Details,
    AlarmDetailsTabItem.Settings,
)

@Composable
fun AlarmDetails(modifier: Modifier = Modifier) {
    var tabIndex by remember { mutableIntStateOf(0) }
    Column(
        modifier = modifier
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            contentColor = Black01,
            indicator = { positions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(positions[tabIndex])
                        .padding(horizontal = 20.dp)
                        .height(2.dp)
                        .background(Black01)
                )
            }
        ) {
            tabItems.forEachIndexed { index, item ->
                val selected = tabIndex == index
                Tab(
                    modifier = Modifier
                        .height(48.dp)
                        .padding(horizontal = 20.dp),
                    selected = selected,
                    selectedContentColor = Black01,
                    unselectedContentColor = Black03,
                    onClick = {
                        tabIndex = index
                    }
                ) {
                    Text(
                        text = stringResource(item.titleId),
                        textAlign = TextAlign.Center,
                        style = if (selected) {
                            MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 15.sp
                            )
                        } else {
                            MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 15.sp,
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmDetailsPreview() {
    AljyoTheme {
        AlarmDetails(
        )
    }
}
package com.asap.aljyo.ui.composable.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.sheet.PasswordSheet
import com.asap.aljyo.ui.composable.main.home.GroupItem
import com.asap.aljyo.ui.theme.Black01
import com.asap.domain.entity.remote.AlarmGroup

@Stable
@Composable
fun SearchResults(
    modifier: Modifier,
    groups: List<AlarmGroup>,
    showFilterSheet: () -> Unit,
    navigateToGroupDetails: (Int) -> Unit,
    navigateToPersonalSetting: (Int) -> Unit,
) {
    val gridState = rememberLazyGridState()

    PasswordSheet(1, navigateToPersonalSetting)

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    append(stringResource(R.string.total))
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append(" ${groups.size}")
                    }
                    append(stringResource(R.string.cases))
                },
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Black01,
                    fontSize = 15.fsp
                )
            )

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable { showFilterSheet() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = "filter icon",
                    tint = Color.Unspecified
                )

                Text(
                    text = stringResource(R.string.filter),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.fsp,
                        color = Color(0xFF111111)
                    )
                )
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 20.dp),
            state = gridState,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(groups.size) {
                GroupItem(alarmGroup = groups[it])
            }
        }
    }
}
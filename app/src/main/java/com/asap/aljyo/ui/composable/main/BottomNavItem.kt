package com.asap.aljyo.ui.composable.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.core.navigation.MainScreenRoute
import com.asap.aljyo.ui.theme.AljyoTheme

sealed class BottomNavItem(
    val icon: Int,
    val label: Int,
    val route: String
) {
    data object Home : BottomNavItem(
        icon = R.drawable.ic_home,
        label = R.string.home,
        route = MainScreenRoute.Home.route,
    )

    data object MyAlarm : BottomNavItem(
        icon = R.drawable.ic_my_alarm,
        label = R.string.alarm_list,
        route = MainScreenRoute.AlarmList.route
    )

    data object MyPage : BottomNavItem(
        icon = R.drawable.ic_my_page,
        label = R.string.my_page,
        route = MainScreenRoute.MyPage.route
    )

    @Composable
    fun Item(
        selected: Boolean,
        onClick: () -> Unit,
    ) {
        TextButton (
            modifier = Modifier.height(48.dp),
            onClick = onClick,
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = if (selected) Color(0xFF222222) else Color(0xFFAAAAAA)
            )
        ) {
            Column(
                modifier = Modifier.fillMaxHeight().padding(vertical = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(icon),
                    contentDescription = "Bottom navigation bar item"
                )

                Text(
                    text = stringResource(label),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 11.fsp,
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavItemPreview() {
    AljyoTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BottomNavItem.Home.Item(selected = true) {}
            BottomNavItem.MyAlarm.Item(selected = false) {}
            BottomNavItem.MyPage.Item(selected = false) {}
        }
    }
}


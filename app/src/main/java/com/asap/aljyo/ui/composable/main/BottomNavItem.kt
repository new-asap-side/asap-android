package com.asap.aljyo.ui.composable.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.Red02
import com.asap.aljyo.ui.theme.White

sealed class BottomNavItem(
    val icon: Int,
    val label: Int,
    val route: String
) {
    data object Home : BottomNavItem(
        icon = R.drawable.ic_home,
        label = R.string.home,
        route = MainRoute.Home.route,
    )

    data object AlarmList : BottomNavItem(
        icon = R.drawable.ic_alarm_list,
        label = R.string.alarm_list,
        route = MainRoute.AlarmList.route
    )

    data object MyPage : BottomNavItem(
        icon = R.drawable.ic_my_page,
        label = R.string.my_page,
        route = MainRoute.MyPage.route
    )
}

@Composable
fun BottomNavItemMain() {
    IconButton(
        onClick = {

        },
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(White)
            .padding(4.dp)
            .clip(CircleShape)
            .background(Red02),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_home),
            tint = Red01,
            contentDescription = "Home icon",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavItemMainPreview() {
    BottomNavItemMain()
}

@Composable
fun BottomNavItemSub(
    isSelected: Boolean = false,
    icon: Int,
    label: Int,
    onClick: () -> Unit,
) {
    val tint = if (isSelected) Red01 else Grey03
    TextButton(
        onClick = onClick,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "bottom navigation item",
                    tint = tint,
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                stringResource(label),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = tint,
                    fontSize = 11.sp,
                    lineHeight = 17.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun BottomNavItemSubPreview() {
    AljyoTheme {
        Row {
            BottomNavItemSub(
                icon = BottomNavItem.AlarmList.icon,
                label = BottomNavItem.AlarmList.label,
                onClick = {}
            )
            Spacer(Modifier.width(10.dp))
            BottomNavItemSub(
                icon = BottomNavItem.MyPage.icon,
                label = BottomNavItem.MyPage.label,
                onClick = {},
            )
        }
    }
}


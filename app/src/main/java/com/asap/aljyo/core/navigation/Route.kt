package com.asap.aljyo.core.navigation


// Application screen
sealed class ScreenRoute(val route: String) {

    data object Onboarding : ScreenRoute(route = "on-boarding")

    data object Main : ScreenRoute(route = "main")

    data object GroupDetails : ScreenRoute(route = "group-details")

    data object Ranking : ScreenRoute(route = "ranking")

    data object ReleaseAlarm : ScreenRoute(route = "release-alarm")

    data object AlarmResult : ScreenRoute(route = "alarm-result")

    data object UserSetting: ScreenRoute(route = "user-setting-profile")

    data object Preferences: ScreenRoute(route = "preferences")

    data object Withdrawal: ScreenRoute(route = "withdrawal")

    data object WithdrawalComplete: ScreenRoute(route = "withdrawal-comoplete")
}

// Main screen route
sealed class MainScreenRoute(val route: String) {

    data object Home : MainScreenRoute(route = "home")

    data object AlarmList : MainScreenRoute(route = "alarm-list")

    data object MyPage : MainScreenRoute(route = "my-page")

}
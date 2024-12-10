package com.asap.aljyo.core.components.navigation


// Application screen
sealed class ScreenRoute(val route: String) {

    data object Onboarding : ScreenRoute(route = "ON_BOARDING")

    data object Main : ScreenRoute(route = "MAIN")

    data object GroupDetails : ScreenRoute(route = "GROUP_DETAILS")

    data object Ranking : ScreenRoute(route = "RANKING")

    data object ReleaseAlarm : ScreenRoute(route = "RELEASE_ALARM")

    data object AlarmResult : ScreenRoute(route = "ALARM_RESULT")

    data object UserSetting: ScreenRoute(route = "USER_SETTING_PROFILE")
}

// Main screen route
sealed class MainScreenRoute(val route: String) {

    data object Home : MainScreenRoute(route = "HOME")

    data object AlarmList : MainScreenRoute(route = "ALARM_LIST")

    data object MyPage : MainScreenRoute(route = "MY_PAGE")

}
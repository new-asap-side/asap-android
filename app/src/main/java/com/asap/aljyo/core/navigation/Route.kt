package com.asap.aljyo.core.navigation


// Application screen
sealed class ScreenRoute(val route: String) {

    data object Onboarding : ScreenRoute(route = "on-boarding")

    data object Main : ScreenRoute(route = "main")

    data object GroupDetails : ScreenRoute(route = "group-details")

    data object Ranking : ScreenRoute(route = "ranking")

    data object AlarmOff : ScreenRoute(route = "alarm-off")

    data object AlarmResult : ScreenRoute(route = "alarm-result")

    data object UserSetting: ScreenRoute(route = "user-setting-profile")

    data object Preferences: ScreenRoute(route = "preferences")

    data object Withdrawal: ScreenRoute(route = "withdrawal")

    data object WithdrawalComplete: ScreenRoute(route = "withdrawal-comoplete")

    data object GroupType: ScreenRoute(route = "group_type")

    data object GroupCreate: ScreenRoute(route = "group_create")

    data object AlarmType: ScreenRoute(route = "alarm_type")

    data object AlarmSetting: ScreenRoute(route = "alarm_Setting")

    data object AlarmMusic: ScreenRoute(route = "alarm_music")

    data object AljyoDescript: ScreenRoute(route = "descript")

    data object GroupEdit: ScreenRoute(route = "group_edit")

    data object PersonalEdit: ScreenRoute(route = "personal_edit")

    data object Report: ScreenRoute(route = "report")

    data object PrivacyPolicy: ScreenRoute(route = "privacy_policy")
}

// Main screen route
sealed class MainScreenRoute(val route: String) {

    data object Home : MainScreenRoute(route = "home")

    data object AlarmList : MainScreenRoute(route = "alarm-list")

    data object MyPage : MainScreenRoute(route = "my-page")

}
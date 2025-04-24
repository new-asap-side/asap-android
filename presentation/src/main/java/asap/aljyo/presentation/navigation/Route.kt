package asap.aljyo.presentation.navigation

sealed class Route(val route: String) {
    data object Onboarding: Route(route = "onboarding")

    data object Profile: Route(route = "profile")

    data object Main: Route(route = "main")
}
package eu.krzdabrowski.starter.core.navigation

sealed class NavigationDestination(
    val route: String,

) {
    data object Rockets : NavigationDestination("rocketsDestination")
    data object Back : NavigationDestination("navigationBack")
    data object RocketDetail : NavigationDestination("rocketsDetailDestination/{rocketId}"){
        fun createRoute(rocketId: String) = "rocketsDetailDestination/$rocketId"
    }
}

package eu.krzdabrowski.starter.basicfeature.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import eu.krzdabrowski.starter.basicfeature.presentation.composable.RocketDetailScreen
import eu.krzdabrowski.starter.basicfeature.presentation.composable.RocketsRoute
import eu.krzdabrowski.starter.core.navigation.NavigationDestination.RocketDetail
import eu.krzdabrowski.starter.core.navigation.NavigationDestination.Rockets
import eu.krzdabrowski.starter.core.navigation.NavigationFactory
import javax.inject.Inject

class RocketsNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {

        builder.composable(Rockets.route) {
            RocketsRoute(navController)
        }

        builder.composable(RocketDetail.route) { backStackEntry ->
            val rocketId = backStackEntry.arguments?.getString("rocketId")
            if (rocketId != null) {
                RocketDetailScreen()
            }
        }
    }

}

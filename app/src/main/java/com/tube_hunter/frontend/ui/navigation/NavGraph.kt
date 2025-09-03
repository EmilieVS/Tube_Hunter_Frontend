package com.tube_hunter.frontend.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tube_hunter.frontend.ui.component.SpotDetailsUi
import com.tube_hunter.frontend.ui.screen.newspot.NewSpotScreen
import com.tube_hunter.frontend.ui.screen.home.HomeScreen
import com.tube_hunter.frontend.ui.screen.spotdetails.SpotDetailsScreen
import com.tube_hunter.frontend.ui.screen.spotlist.SpotListScreen
import com.tube_hunter.frontend.ui.screen.spotlist.SpotListViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SpotList : Screen("spot_list")
    object SpotDetails : Screen("spot_details/{spotId}") {
        fun createRoute(spotId: String) = "spot_details/$spotId"
    }
    object NewSpot : Screen("add_spot")
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.SpotList.route) {
            SpotListScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(
            route = Screen.SpotDetails.route,
            arguments = listOf(navArgument("spotId") { defaultValue = "" })
        ) { backStackEntry ->
            val spotId = backStackEntry.arguments?.getString("spotId") ?: ""
            SpotDetailsScreen(
                spotId = spotId,
                onNavigate = { route -> navController.navigate(route) })
        }

        composable(Screen.NewSpot.route) {
            NewSpotScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }
    }
}
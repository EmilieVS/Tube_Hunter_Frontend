package com.tube_hunter.frontend.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tube_hunter.frontend.ui.screen.newspot.NewSpotScreen
import com.tube_hunter.frontend.ui.screen.home.HomeScreen
import com.tube_hunter.frontend.ui.screen.spotdetails.SpotDetailsScreen
import com.tube_hunter.frontend.ui.screen.spotlist.SpotListScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SpotList : Screen("spot_list")
    object SpotDetails : Screen("spot_details/{spotId}") {
        fun createRoute(spotId: Long) = "spot_details/$spotId"
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

        composable(
            route = Screen.SpotList.route + "?message={message}",
            arguments = listOf(navArgument("message") { defaultValue = "" })
        ) { backStackEntry ->
            val message = backStackEntry.arguments?.getString("message") ?: ""
            SpotListScreen(
                onNavigate = { route -> navController.navigate(route) },
                snackbarMessage = message
            )
        }

        composable(
            route = Screen.SpotDetails.route,
            arguments = listOf(navArgument("spotId") {
                type = NavType.LongType
                defaultValue = -1L
            })
        ) { backStackEntry ->
            val spotId = backStackEntry.arguments?.getLong("spotId") ?: -1L
            SpotDetailsScreen(
                spotId = spotId,
                onNavigate = { route -> navController.navigate(route) }
            )
        }


        composable(Screen.NewSpot.route) {
            NewSpotScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }
    }
}
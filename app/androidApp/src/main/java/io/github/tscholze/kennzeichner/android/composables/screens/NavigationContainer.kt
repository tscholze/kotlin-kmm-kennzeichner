package io.github.tscholze.kennzeichner.android.composables.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * Contains the navigation container with all it's composable.
 */
@Composable
fun NavigationContainer() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "regions") {
        // Regions
        composable("regions") {
            RegionsScreen(navController)
        }

        // Region detail
        composable("regions/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if(id != null) {
                RegionScreen(regionId = id, navController)
            } else {
                TODO()
            }
        }

        // Map
        composable("map") {
            MapScreen(navController)
        }
    }
}
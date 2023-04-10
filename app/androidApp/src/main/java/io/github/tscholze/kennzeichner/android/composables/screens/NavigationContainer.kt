package io.github.tscholze.kennzeichner.android.composables.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.tscholze.kennzeichner.android.composables.components.BottomNavigationItemData
import io.github.tscholze.kennzeichner.android.composables.screens.regions.list.RegionsScreen

/**
 * Contains the navigation container with all it's composable.
 */
@Composable
fun NavigationContainer() {
    // MARK: - Properties -

    val navController = rememberNavController()

    // MARK: - Navigation host -

    NavHost(navController = navController, startDestination = "regions") {
        // Regions
        composable(BottomNavigationItemData.List.route) {
            RegionsScreen(navController)
        }

        // Region's detail
        composable("${BottomNavigationItemData.List.route}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "A"
            RegionScreen(regionId = id, navController)
        }

        // Map
        composable(BottomNavigationItemData.Map.route) {
            MapScreen(navController)
        }
    }
}
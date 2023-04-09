package io.github.tscholze.kennzeichner.android.composables.screens

import androidx.compose.runtime.Composable
import com.microsoft.device.dualscreen.twopanelayout.twopanelayoutnav.composable
import androidx.navigation.compose.rememberNavController
import com.microsoft.device.dualscreen.twopanelayout.Screen
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayoutNav

/**
 * Contains the navigation container with all it's composable.
 */
@Composable
fun NavigationContainer() {

    // MARK: - Properties -

    val navController = rememberNavController()

    // MARK: - Navigation -

    TwoPaneLayoutNav(
        navController = navController,
        singlePaneStartDestination = "regions",
        pane1StartDestination = "regions",
        pane2StartDestination = "map"
    ) {
        // Regions
        composable("regions") {
            RegionsScreen(navController) {region ->
                navController.navigateTo("regions/${region.id}", Screen.Pane2 )
            }
        }

        // Region's detail
        composable("regions/{id}") { twoPaneBackStack ->
            val id = twoPaneBackStack.arguments?.getString("id") ?: "A"
            RegionScreen(regionId = id, navController)
        }

        // Map
        composable("map") {
            MapScreen(navController)
        }
    }
}
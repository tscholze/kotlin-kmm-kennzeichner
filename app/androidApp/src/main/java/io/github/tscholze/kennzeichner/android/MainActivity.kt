package io.github.tscholze.kennzeichner.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.tscholze.kennzeichner.android.composables.screens.RegionScreen
import io.github.tscholze.kennzeichner.android.composables.screens.RegionsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "regions") {
                composable("regions") {
                    RegionsScreen(
                        navigateToRegion = { id -> navController.navigate("regions/${id}")}
                    )
                }
                composable("regions/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")
                    if(id != null) {
                        RegionScreen(regionId = id)
                    } else {
                        TODO()
                    }
                }
            }
        }
    }
}
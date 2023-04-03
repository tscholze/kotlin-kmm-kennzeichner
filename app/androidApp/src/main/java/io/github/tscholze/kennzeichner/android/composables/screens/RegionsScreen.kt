package io.github.tscholze.kennzeichner.android.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import io.github.tscholze.kennzeichner.android.R
import io.github.tscholze.kennzeichner.android.composables.components.LoadingIndicator
import io.github.tscholze.kennzeichner.android.composables.components.RegionDetails
import io.github.tscholze.kennzeichner.android.composables.components.RegionMap
import io.github.tscholze.kennzeichner.android.composables.layouts.PageLayout
import io.github.tscholze.kennzeichner.data.LicensePlateRepository
import io.github.tscholze.kennzeichner.data.Region
import kotlinx.coroutines.launch

/**
 * Represents a list based view of all regions.
 *
 * @param navController Navigation controller to be used
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegionsScreen(navController: NavController) {
    PageLayout(stringResource(id = R.string.regions_title), navController) {
        val scope = rememberCoroutineScope()
        var regions by remember { mutableStateOf(emptyList<Region>()) }

        // MARK: - On Start up -

        LaunchedEffect(true) {
            scope.launch {
                regions = try {
                    LicensePlateRepository().fetchRegions()
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }

        // MARK: - UI -

        if(regions.isEmpty()) {
            LoadingIndicator()
        } else {
            LazyColumn(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(regions){ region ->
                    Card(
                        elevation = 8.dp,
                        modifier = Modifier.fillMaxSize(),
                        onClick = { navController.navigate("regions/${region.id}") }
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            // Map
                            RegionMap(
                                region = region,
                                modifier = Modifier
                                    .height(150.dp)
                                    .fillMaxWidth()
                            )

                            // Details
                            RegionDetails(region = region)
                        }
                    }
                }
            }
        }
    }
}
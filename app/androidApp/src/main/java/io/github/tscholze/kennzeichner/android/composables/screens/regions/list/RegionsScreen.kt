@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package io.github.tscholze.kennzeichner.android.composables.screens.regions.list

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.tscholze.kennzeichner.android.R
import io.github.tscholze.kennzeichner.android.composables.components.LoadingIndicator
import io.github.tscholze.kennzeichner.android.composables.components.RegionDetails
import io.github.tscholze.kennzeichner.android.composables.components.RegionMap
import io.github.tscholze.kennzeichner.android.composables.components.SearchBar
import io.github.tscholze.kennzeichner.android.composables.layouts.PageLayout
import io.github.tscholze.kennzeichner.data.Region
import org.koin.androidx.compose.koinViewModel

/**
 * Represents a list based view of all regions.
 *
 * @param navController Navigation controller to be used
 */
@Composable
fun RegionsScreen(navController: NavController, viewModel: RegionsViewModel = koinViewModel()) {
    PageLayout(stringResource(id = R.string.regions_title), navController) {

        // MARK: - Properties -

        val uiState by viewModel.uiState.collectAsState()

        // MARK: - UI -

        // Create content dependent on the ui state.
        when (uiState) {
            // Loading
            RegionsUiState.Loading -> LoadingIndicator()

            // List | why the cast?
            is RegionsUiState.Success -> RegionsList(
                (uiState as RegionsUiState.Success).regions,
                onRegionSelected = { navController.navigate("regions/${it.id}") }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RegionsList(regions: List<Region>, onRegionSelected: (Region) -> Unit) {

    // MARK: - Properties -

    val searchQuery = remember { mutableStateOf("") }

    // MARK: - Helper functions -

    fun filteredRegions(): List<Region> {
        val query = searchQuery.value

        if(query.trim().isEmpty()) {
            return regions
        }

        return regions.filter {
            it.id.startsWith(query, ignoreCase = true) || it.name.startsWith(query, ignoreCase = true)
        }
    }

    // MARK: - UI -

    // Search bar
    SearchBar(state = searchQuery)

    LazyColumn(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(filteredRegions()) { region ->
            Card(
                elevation = 8.dp,
                modifier = Modifier.fillMaxSize(),
                onClick = { onRegionSelected(region) }
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
                    RegionDetails(region)
                }
            }
        }
    }
}

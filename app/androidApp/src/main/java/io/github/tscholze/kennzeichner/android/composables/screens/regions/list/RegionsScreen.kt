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
import androidx.compose.runtime.MutableState
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
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegionsScreen(navController: NavController, viewModel: RegionsViewModel = koinViewModel()) {
    PageLayout(stringResource(id = R.string.regions_title), navController) {
        val searchQuery = remember { mutableStateOf("") }
        val uiState by viewModel.uiState.collectAsState()

        // MARK: - UI -

        when(uiState) {
            is RegionsUiState.Success -> ShowContent(
                searchQuery,
                (uiState as RegionsUiState.Success).regions,
                onRegionSelected = { navController.navigate("regions/${it.id}") }
            ) // why the cast?
            RegionsUiState.Loading -> LoadingIndicator()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowContent(searchQuery: MutableState<String>, regions: List<Region>, onRegionSelected: (Region) -> Unit) {
    // Search bar
    SearchBar(state = searchQuery)

    // Content
    LazyColumn(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(regions){ region ->
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
                    RegionDetails(region = region)
                }
            }
        }
    }
}
package io.github.tscholze.kennzeichner.android.composables.screens.region

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.tscholze.kennzeichner.android.R
import io.github.tscholze.kennzeichner.android.composables.components.LoadingIndicator
import io.github.tscholze.kennzeichner.android.composables.components.RegionInformation
import io.github.tscholze.kennzeichner.android.composables.components.RegionMap
import io.github.tscholze.kennzeichner.android.composables.layouts.PageLayout
import io.github.tscholze.kennzeichner.android.composables.screens.regions.list.RegionUiState
import io.github.tscholze.kennzeichner.data.Region
import org.koin.androidx.compose.koinViewModel

/**
 * Represents detail information about given region.
 *
 * @param navController Navigation controller to be used
 * @param viewModel Related view model, injected by DI
 */
@Composable
fun RegionScreen(
    navController: NavController,
    viewModel: RegionViewModel = koinViewModel()
) {
    PageLayout(stringResource(R.string.regions_title), navController) {
        // MARK: - Properties -

        val uiState by viewModel.uiState.collectAsState()

        // MARK: - UI -

        // Create content dependent on the ui state.
        when (val state = uiState) {
            // Loading
            RegionUiState.Loading -> LoadingIndicator()

            // List | why the cast?
            is RegionUiState.Success -> RegionDetail(state.region)
        }
    }
}

@Composable
private fun RegionDetail(region: Region) {
    Column(
        modifier = Modifier.padding(12.dp),
    ) {

        // Details
        RegionInformation(region)

        // Map
        RegionMap(
            region = region,
            modifier = Modifier.fillMaxSize()
        )
    }
}
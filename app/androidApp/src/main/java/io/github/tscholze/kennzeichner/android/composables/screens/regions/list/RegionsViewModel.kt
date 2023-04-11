package io.github.tscholze.kennzeichner.android.composables.screens.regions.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.tscholze.kennzeichner.data.LicensePlateRepository
import io.github.tscholze.kennzeichner.data.Region
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Defines the data set of the regions screen.
 *
 * @param repository License plate list data source.
 */
class RegionsViewModel(
    private val repository: LicensePlateRepository
): ViewModel() {

    // MARK: - Properties & backing fields -

    private var _uiState = MutableStateFlow<RegionsUiState>(RegionsUiState.Loading)
    val uiState: StateFlow<RegionsUiState> = _uiState

    // MARK: - Init -

    init {
        viewModelScope.launch {
            _uiState.value = RegionsUiState.Success(repository.fetchRegions())
        }
    }

    // MARK: - Helper -

    /**
     * Filters region by given query.
     *
     * @param query Search query
     * @return List of filtered regions
     */
    fun filterRegionsByQuery(query: String): List<Region> {
        return when(_uiState.value) {
            RegionsUiState.Loading -> emptyList()
            is RegionsUiState.Success -> repository.regionsForSearchQuery(query)
        }
    }
}


/**
 * Defines all ui states of the fetched regions screen
 */
sealed class RegionsUiState {
    data class Success(val regions: List<Region>): RegionsUiState()
    object Loading: RegionsUiState()
}

/**
 * Defines all ui states of a fetched region screen
 */
sealed class RegionUiState {
    data class Success(val region: Region): RegionUiState()
    object Loading: RegionUiState()
}
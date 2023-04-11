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
        fun filter(regions: List<Region>, query: String): List<Region> {
            if(query.trim().isEmpty()) {
                return regions
            }

            return regions.filter {
                it.id.startsWith(query, ignoreCase = true) || it.name.startsWith(query, ignoreCase = true)
            }
        }

        return when(val state = _uiState.value) {
            RegionsUiState.Loading -> emptyList()
            is RegionsUiState.Success -> filter(state.regions, query)
        }
    }
}

/**
 * Defines all ui states of the regions screen.
 */
sealed class RegionsUiState {
    data class Success(val regions: List<Region>): RegionsUiState()
    object Loading: RegionsUiState()
}
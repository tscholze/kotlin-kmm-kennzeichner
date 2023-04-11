package io.github.tscholze.kennzeichner.android.composables.screens.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.tscholze.kennzeichner.android.composables.screens.regions.list.RegionUiState
import io.github.tscholze.kennzeichner.data.LicensePlateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Defines the data set of the region screen
 *
 * @param regionId Id of the region that shall be shown
 * @param repository License plate list data source.
 */
class RegionViewModel(
    private val regionId: String,
    private val repository: LicensePlateRepository
): ViewModel() {

    // MARK: - Properties & backing fields -

    private var _uiState = MutableStateFlow<RegionUiState>(RegionUiState.Loading)
    val uiState: StateFlow<RegionUiState> = _uiState

    // MARK: - Init -

    init {
        viewModelScope.launch {
            _uiState.value = RegionUiState.Success(repository.regionForId(regionId))
        }
    }
}
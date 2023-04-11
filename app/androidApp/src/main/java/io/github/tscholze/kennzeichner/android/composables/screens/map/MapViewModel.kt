package io.github.tscholze.kennzeichner.android.composables.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.tscholze.kennzeichner.android.composables.screens.regions.list.RegionsUiState
import io.github.tscholze.kennzeichner.data.LicensePlateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Defines the data set of the map screen.
 *
 * @param repository License plate list data source.
 */
class MapViewModel(
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
}
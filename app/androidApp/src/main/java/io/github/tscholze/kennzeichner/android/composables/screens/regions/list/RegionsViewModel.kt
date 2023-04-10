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
}

/**
 * Defines all ui states of the regions screen.
 */
sealed class RegionsUiState {
    data class Success(val regions: List<Region>): RegionsUiState()
    object Loading: RegionsUiState()
}

/**
 * Defines all possible actions of the regions screen.
 */
sealed class RegionsActions {
    data class NavigateToRegion(val region: Region)
}
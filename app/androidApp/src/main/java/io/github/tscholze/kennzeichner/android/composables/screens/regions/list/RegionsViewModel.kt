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

    private val _uiState = MutableStateFlow(RegionsUiState.Success(emptyList()))
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
    data class Success(val news: List<Region>): RegionsUiState()
    object Loading: RegionsUiState()
}
package io.github.tscholze.kennzeichner.android.di

import io.github.tscholze.kennzeichner.android.composables.screens.map.MapViewModel
import io.github.tscholze.kennzeichner.android.composables.screens.region.RegionViewModel
import io.github.tscholze.kennzeichner.android.composables.screens.regions.list.RegionsViewModel
import io.github.tscholze.kennzeichner.data.LicensePlateRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin's app modules.
 */
val appModule = module {
    single { LicensePlateRepository() }
    viewModel { RegionsViewModel(get()) }
    viewModel { MapViewModel(get()) }
    viewModel { p -> RegionViewModel(p[0], get())  }
}
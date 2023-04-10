package io.github.tscholze.kennzeichner.android.di

import io.github.tscholze.kennzeichner.android.composables.screens.regions.list.RegionsViewModel
import io.github.tscholze.kennzeichner.data.LicensePlateRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

    val appModule = module {
        single { LicensePlateRepository() }
        viewModel { RegionsViewModel(get()) }
    }
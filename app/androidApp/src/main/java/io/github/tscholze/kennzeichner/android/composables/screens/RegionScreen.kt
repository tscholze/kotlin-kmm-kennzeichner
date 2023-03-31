package io.github.tscholze.kennzeichner.android.composables.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.tscholze.kennzeichner.android.composables.layouts.PageLayout
import io.github.tscholze.kennzeichner.data.LicensePlateRepository
import io.github.tscholze.kennzeichner.data.Region

@Composable
fun RegionScreen(regionId: String) {
    PageLayout("Kennzeichen") {

        var region by remember { mutableStateOf<Region?>(null) }

        LaunchedEffect(key1 = true) {
            LicensePlateRepository().fetchRegions()
            region = LicensePlateRepository().regionForId(regionId)
        }

        Text(text = region?.name ?: "Nicht geladen")
    }
}
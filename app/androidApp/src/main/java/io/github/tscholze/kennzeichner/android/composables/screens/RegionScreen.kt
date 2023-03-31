package io.github.tscholze.kennzeichner.android.composables.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.tscholze.kennzeichner.android.composables.layouts.PageLayout
import io.github.tscholze.kennzeichner.data.LicensePlateRepository
import io.github.tscholze.kennzeichner.data.Region

@Composable
fun RegionScreen(regionId: String) {
    PageLayout("Kennzeichen") {

        // MARK: - Properties -

        var region by remember { mutableStateOf<Region?>(null) }

        // MARK: - LaunchEffect -
        LaunchedEffect(key1 = true) {
            region = LicensePlateRepository().regionForId(regionId)
        }

        if(region != null) {
            Column(
                modifier = Modifier.padding(12.dp),
            ) {
                Text(text = region?.name ?: "Nicht geladen")
            }
        }
    }
}
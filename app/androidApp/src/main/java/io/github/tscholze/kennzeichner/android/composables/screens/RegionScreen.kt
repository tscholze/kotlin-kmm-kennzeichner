package io.github.tscholze.kennzeichner.android.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.tscholze.kennzeichner.android.composables.components.RegionDetails
import io.github.tscholze.kennzeichner.android.composables.components.RegionMap
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

        // MARK: - UI -

        if(region == null) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Daten laden ...", textAlign = TextAlign.Center)
            }
        } else {
            Column(
                modifier = Modifier.padding(12.dp),
            ) {

                // Details
                RegionDetails(region = region!!)

                // Map
                RegionMap(
                    region = region!!,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
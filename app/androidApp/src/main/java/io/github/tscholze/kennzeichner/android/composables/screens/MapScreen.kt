package io.github.tscholze.kennzeichner.android.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import io.github.tscholze.kennzeichner.android.R
import io.github.tscholze.kennzeichner.android.composables.components.LoadingIndicator
import io.github.tscholze.kennzeichner.android.composables.components.RegionDetails
import io.github.tscholze.kennzeichner.android.composables.layouts.PageLayout
import io.github.tscholze.kennzeichner.data.LicensePlateRepository
import io.github.tscholze.kennzeichner.data.Region
import kotlinx.coroutines.launch

/**
 * Represents a map based view of all regions.
 *
 * @param navController Navigation controller to be used
 */
@Composable
fun MapScreen(navController: NavController) {
    PageLayout(title = stringResource(R.string.maps_title), navController) {
        val scope = rememberCoroutineScope()
        var regions by remember { mutableStateOf(emptyList<Region>()) }
        var selectedRegion by remember { mutableStateOf<Region?>(null) }

        // MARK: - On Start up -

        LaunchedEffect(true) {
            scope.launch {
                regions = try {
                    LicensePlateRepository().fetchRegions()
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }

        // MARK: - UI -

        if (regions.isEmpty()) {
            LoadingIndicator()
        } else {

            // Z-Index box
            Box {
                // Show Meta information if a region is selected
                if (selectedRegion != null) {
                    Box(modifier = Modifier
                        .zIndex(2f)
                        .padding(12.dp)
                        .alpha(0.8f)) {
                        Box(
                            modifier = Modifier
                                .zIndex(2f)
                                .background(MaterialTheme.colors.surface)
                                .border(2.dp, MaterialTheme.colors.onSurface)
                                .padding(8.dp)
                        ) {

                            RegionDetails(region = selectedRegion!!)
                        }
                    }
                }

                // Starting position
                // Roughly centered Germany
                val initialCoordinates = LatLng(
                    50.55210868137082,
                    10.232996206166408
                )

                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(
                        initialCoordinates,
                        6f
                    )
                }

                GoogleMap(
                    modifier = Modifier.zIndex(1f),
                    cameraPositionState = cameraPositionState
                ) {
                    regions.map { region ->

                        val coordinates = LatLng(
                            region.lat.replace(",", ".").toDouble(),
                            region.long.replace(",", ".").toDouble()
                        )

                        Marker(
                            state = rememberMarkerState(position = coordinates),
                            title = region.id,
                            snippet = region.name,
                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE),
                            onInfoWindowClick = { selectedRegion = region },
                            onClick = { _ ->
                                selectedRegion = region
                                false
                            }
                        )
                    }
                }
            }
        }
    }
}
package io.github.tscholze.kennzeichner.android.composables.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import io.github.tscholze.kennzeichner.data.Region

@Composable
fun RegionMap(region: Region, modifier: Modifier) {
    val coordinates = LatLng(
        region.lat.replace(",", ".").toDouble(),
        region.long.replace(",", ".").toDouble()
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            coordinates,
            10f
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    )
}
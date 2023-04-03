package io.github.tscholze.kennzeichner.android.composables.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import io.github.tscholze.kennzeichner.data.Coordinate
import io.github.tscholze.kennzeichner.data.Region

/**
 * Renders a map with centered on given region.
 *
 * @param region: Region that shall be rendered.
 */
@Composable
fun RegionMap(region: Region, modifier: Modifier) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(region.coordinate.latitude, region.coordinate.longitude),
            10f
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    )
}
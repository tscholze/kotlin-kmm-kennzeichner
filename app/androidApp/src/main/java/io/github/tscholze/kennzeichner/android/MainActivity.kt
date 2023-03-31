package io.github.tscholze.kennzeichner.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import io.github.tscholze.kennzeichner.android.composables.PageLayout
import io.github.tscholze.kennzeichner.data.LicensePlateRepository
import io.github.tscholze.kennzeichner.data.Region
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PageLayout(title = "Alle Kennzeichen") {
                val scope = rememberCoroutineScope()
                var regions by remember { mutableStateOf(emptyList<Region>()) }

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

                LazyColumn(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(regions) { region ->
                        Card(
                            elevation = 8.dp,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
                                    modifier = Modifier
                                        .height(150.dp)
                                        .fillMaxWidth(),
                                    cameraPositionState = cameraPositionState
                                )

                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Id
                                    Text(region.id, style = MaterialTheme.typography.h3)

                                    // Information
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        // Name
                                        Text(region.name, fontWeight = FontWeight.Bold)

                                        // Meta information
                                        Column {
                                            // Leader
                                            if (region.leader.isNotEmpty()) {
                                                Text("Bürgermeister*in: ${region.leader}", style = MaterialTheme.typography.caption)
                                            }

                                            if (region.inhabitants.isNotEmpty()) {
                                                Text("Einwohner: ${region.inhabitants}", style = MaterialTheme.typography.caption)
                                            }

                                            if (region.area.isNotEmpty()) {
                                                Text("Fläche: ${region.inhabitants} qkm", style = MaterialTheme.typography.caption)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
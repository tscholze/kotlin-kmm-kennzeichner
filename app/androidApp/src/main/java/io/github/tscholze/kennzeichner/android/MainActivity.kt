package io.github.tscholze.kennzeichner.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(region.id)
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(region.name)

                                    if (region.leader.isEmpty()) {
                                        Text("unbekannt")
                                    } else {
                                        Text(region.leader)
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
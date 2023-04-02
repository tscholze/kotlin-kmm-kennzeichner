package io.github.tscholze.kennzeichner.android.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.tscholze.kennzeichner.android.R
import io.github.tscholze.kennzeichner.data.Region

/**
 * Renders region's text-based details.
 *
 * @param region: Region that shall be rendered.
 */
@Composable
fun RegionDetails(region: Region) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Id
        Text(region.id, style = MaterialTheme.typography.h3)

        // Information
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Name
            RegionDetailText(region.name, FontWeight.Bold)

            // Meta information
            Column(modifier = Modifier.fillMaxWidth()) {

                // Leader
                if (region.leader.isNotEmpty()) {
                    RegionDetailText(
                        stringResource(id = R.string.region_meta_leader_format, region.leader)
                    )
                }

                // Inhabitants
                if (region.inhabitants.isNotEmpty()) {
                    RegionDetailText(
                        stringResource(id = R.string.region_meta_inhabitants_format, region.inhabitants)
                    )
                }

                // Area
                if (region.area.isNotEmpty()) {
                    RegionDetailText(
                        stringResource(id = R.string.region_meta_area_format, region.area)
                    )
                }
            }
        }
    }
}

// MARK: - Sub composable -

@Composable
fun RegionDetailText(
    text: String,
    weight: FontWeight = FontWeight.Normal
) {
    Text(
        text,
       // textAlign = TextAlign.Right,
        fontWeight = weight,
       // modifier = Modifier.fillMaxWidth()
    )
}
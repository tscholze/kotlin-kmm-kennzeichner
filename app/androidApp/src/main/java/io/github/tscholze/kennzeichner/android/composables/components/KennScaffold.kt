package io.github.tscholze.kennzeichner.android.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.github.tscholze.kennzeichner.android.R

// MARK: - Composable -

/**
 * App-themed scaffold with a top and bottom bar.
 *
 * Caution:
 *  The title has to be the same as for the AppScreen.*
 *  value. This is used to mark the tab item as selected.
 *
 * @param title Title of the screen
 * @param content Content of the scaffold
 */
@Composable
internal fun KennScaffold(
    title: String,
    navController: NavController,
    content: @Composable () -> Unit
) {
    // MARK: - Helper -

    val uriHandler = LocalUriHandler.current

    // MARK: - Components -

    @Composable
    fun KennAppBar() {
        TopAppBar(
            title = { Text(title) },
            actions = {
                Button(
                    onClick = {
                        uriHandler.openUri("https://github.com/tscholze/kotlin-kmm-kennzeichner")
                    },
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(R.string.button_fork_me_title))
                        Icon(
                            Icons.Rounded.Face,
                            contentDescription = stringResource(R.string.button_fork_me_title)
                        )
                    }

                }
            }
        )
    }

    @Composable
    fun KennBottomAppBar() {
        BottomAppBar {
            // List
            BottomNavigationItem(
                icon = {
                    Icon(
                        Icons.Rounded.List,
                        contentDescription = stringResource(R.string.tabitem_list_title)
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.tabitem_list_title),
                        fontSize = 10.sp
                    )
                },
                selected = false,
                onClick = {
                    navController.navigate("regions")
                }
            )

            // Map
            BottomNavigationItem(
                icon = {
                    Icon(
                        Icons.Rounded.Place,
                        contentDescription = stringResource(R.string.tabitem_map_title)
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.tabitem_map_title),
                        fontSize = 10.sp
                    )
                },
                selected = false,
                onClick = {
                    navController.navigate("map")
                }
            )
        }
    }

    // MARK: - UI -

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        Scaffold(
            topBar = { KennAppBar() },
            bottomBar = { KennBottomAppBar() },
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                content()
            }
        }
    }
}
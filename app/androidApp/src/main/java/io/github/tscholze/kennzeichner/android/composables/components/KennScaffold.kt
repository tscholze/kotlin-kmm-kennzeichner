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
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                        Text("Fork me")
                        Icon(
                            Icons.Rounded.Face,
                            contentDescription = "Go to GitHub"
                        )
                    }

                }
            }
        )
    }

    @Composable
    fun KennBottomAppBar() {
        BottomAppBar {
            BottomNavigationItem(
                icon = {
                    Icon(
                        Icons.Rounded.Home,
                        contentDescription = "Liste"
                    )
                },
                label = {
                    Text(
                        text = "Liste",
                        fontSize = 10.sp
                    )
                },
                selected = false,
                onClick = {
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
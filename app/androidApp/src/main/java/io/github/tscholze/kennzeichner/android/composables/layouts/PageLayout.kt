package io.github.tscholze.kennzeichner.android.composables.layouts

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.github.tscholze.kennzeichner.android.KennTheme
import io.github.tscholze.kennzeichner.android.composables.components.KennScaffold

/**
 * Themed-styled layout for a full screen styled page.
 */
@Composable
internal fun PageLayout(
    title: String, navController: NavController,
    content: @Composable () -> Unit
) {
    KennTheme {
        KennScaffold(title, navController) {
            content()
        }
    }
}
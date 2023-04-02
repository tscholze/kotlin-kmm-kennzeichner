package io.github.tscholze.kennzeichner.android.composables.layouts

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.github.tscholze.kennzeichner.android.composables.components.KennScaffold
import io.github.tscholze.kennzeichner.android.theme.KennTheme

/**
 * Themed-styled layout for a full screen styled page.
 *
 * @param title Title of the page
 * @param navController Used navigation controlled.
 * @param content Content of the page.
 */
@Composable
internal fun PageLayout(
    title: String,
    navController: NavController,
    content: @Composable () -> Unit
) {
    KennTheme {
        KennScaffold(title, navController) {
            content()
        }
    }
}
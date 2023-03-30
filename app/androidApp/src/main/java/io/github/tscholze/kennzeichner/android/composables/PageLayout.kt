package io.github.tscholze.kennzeichner.android.composables

import androidx.compose.runtime.Composable
import io.github.tscholze.kennzeichner.android.KennTheme

/**
 * Themed-styled layout for a full screen styled page.
 */
@Composable
internal fun PageLayout(title: String, content: @Composable () -> Unit) {
    KennTheme {
        KennScaffold(title) {
            content()
        }
    }
}
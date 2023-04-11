package io.github.tscholze.kennzeichner.android.composables.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import io.github.tscholze.kennzeichner.android.R

/**
 * Represents a search bar.
 * If the user types or clears the search bar text the state
 * property will be updated.
 *
 * @param state: Observable state of the search query.
 */
@Composable
fun SearchBar(state: MutableState<String>) {
    // MARK: - Properties -

    val focusManager = LocalFocusManager.current

    // MARK: - UI -

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.value,
        onValueChange = { state.value = it },
        singleLine = true,
        placeholder = { Text(stringResource(R.string.searchbar_text_placeholder))
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search icon",
            )
        },
        trailingIcon = {
            if (state.value.isNotEmpty()) {
                IconButton(
                    onClick = {
                        state.value = ""
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Clear icon",
                    )
                }
            }
        },
        keyboardActions = KeyboardActions(
            onAny = { focusManager.clearFocus() }
        ),
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            imeAction = ImeAction.Search
        )
    )
}
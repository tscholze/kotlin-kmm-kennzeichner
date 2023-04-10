package io.github.tscholze.kennzeichner.android.composables.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun SearchBar(state: MutableState<String>) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.value,
        onValueChange = {
            print(it)
            state.value = it
                        },
        singleLine = true,
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search icon",
            )
        },
        trailingIcon = {
            if (state.value.isNotEmpty()) {
                IconButton(onClick = { state.value = "" }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Clear icon",
                    )
                }
            }
        },
    )
}
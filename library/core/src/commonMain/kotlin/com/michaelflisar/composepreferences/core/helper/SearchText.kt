package com.michaelflisar.composepreferences.core.helper

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.michaelflisar.composepreferences.core.filter.LocalPreferenceFilter

@Composable
fun SearchText(
    text: String
) {
    val filter = LocalPreferenceFilter.current
    if (filter == null) {
        Text(text)
    } else {
        val annotated by remember(text, filter) {
            derivedStateOf {
                filter.highlight(text)
            }
        }
        Text(annotated)
    }
}
package com.michaelflisar.composepreferences.core.helper

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import com.michaelflisar.composepreferences.core.filter.LocalPreferenceFilter

@Composable
fun SearchText(
    text: String,
    renderer: @Composable (text: AnnotatedString) -> Unit
) {
    val filter = LocalPreferenceFilter.current
    val annotated by remember(text, filter) {
        derivedStateOf {
            filter?.highlight(text) ?: buildAnnotatedString { append(text) }
        }
    }
    renderer(annotated)
}
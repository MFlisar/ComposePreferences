package com.michaelflisar.composepreferences.core.helper

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceFilter
import com.michaelflisar.composepreferences.core.classes.PreferenceFilter

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
                buildHighlightedString(text, filter)
            }
        }
        Text(annotated)
    }
}

private fun buildHighlightedString(
    text: String,
    filter: PreferenceFilter
): AnnotatedString {
    return buildAnnotatedString {
        append(text)
        val parts = when (filter.mode.value) {
            PreferenceFilter.Mode.ContainsText -> listOf(filter.search.value)
            PreferenceFilter.Mode.AllWords,
            PreferenceFilter.Mode.AnyWord -> PreferenceFilter.words(filter.search.value, filter.ignoreCase.value).map { it.text }
        }.filter { it.isNotEmpty() }
        for (part in parts) {
            var offset = 0
            var startIndex = 0
            while (text.indexOf(part, startIndex = offset, ignoreCase = filter.ignoreCase.value)
                    .also { startIndex = it } != -1
            ) {
                val endIndex = startIndex + part.length
                addStyle(style = filter.highlightSpan, start = startIndex, end = endIndex)
                offset = endIndex
            }
        }
    }
}
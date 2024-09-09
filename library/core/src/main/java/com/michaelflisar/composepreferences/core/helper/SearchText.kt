package com.michaelflisar.composepreferences.core.helper

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceFilter
import com.michaelflisar.composepreferences.core.classes.PreferenceFilter
import com.michaelflisar.composepreferences.core.classes.rememberPreferenceFilter

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
        val parts = when (filter.mode) {
            PreferenceFilter.Mode.Exakt -> listOf(filter.search.value)
            PreferenceFilter.Mode.AllWords,
            PreferenceFilter.Mode.AnyWord -> PreferenceFilter.words(filter.search.value)
        }
        for (part in parts) {
            var offset = 0
            var startIndex = 0
            while (text.indexOf(part, startIndex = offset, ignoreCase = filter.ignoreCase)
                    .also { startIndex = it } != -1
            ) {
                val endIndex = startIndex + part.length
                addStyle(style = filter.highlightSpan, start = startIndex, end = endIndex)
                offset = endIndex
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    MaterialTheme {
        Surface {
            Column {
                val highlightSpan = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)
                val filter1 = rememberPreferenceFilter(
                    "ha yo",
                    mode = PreferenceFilter.Mode.Exakt,
                    highlightSpan = highlightSpan
                )
                val filter2 = rememberPreferenceFilter(
                    "ha yo",
                    mode = PreferenceFilter.Mode.AnyWord,
                    highlightSpan = highlightSpan
                )
                val filter3 = rememberPreferenceFilter(
                    "ha yo",
                    mode = PreferenceFilter.Mode.AllWords,
                    highlightSpan = highlightSpan
                )
                val filter4 = rememberPreferenceFilter(
                    "ha yo 4c",
                    mode = PreferenceFilter.Mode.AllWords,
                    highlightSpan = highlightSpan
                )
                PreferenceScreen(filter = filter1, scrollable = false) {
                    PreferenceInfo(title = "Hallo 1", subtitle = "Hallo you")
                }
                PreferenceScreen(filter = filter2, scrollable = false) {
                    PreferenceInfo(title = "Hallo 2a", subtitle = "Hallo")
                    PreferenceInfo(title = "Hallo 2b", subtitle = "Hallo you")
                    PreferenceInfo(title = "Hallo 2c", subtitle = "Hallo you")
                }
                PreferenceScreen(filter = filter3, scrollable = false) {
                    PreferenceInfo(title = "Hallo 3a", subtitle = "Hallo")
                    PreferenceInfo(title = "Hallo 3b", subtitle = "Hallo you")
                    PreferenceInfo(title = "Hallo 3c", subtitle = "Hallo you")
                }
                PreferenceScreen(filter = filter4, scrollable = false) {
                    PreferenceInfo(title = "Hallo 4a", subtitle = "Hallo")
                    PreferenceInfo(title = "Hallo 4b", subtitle = "Hallo you")
                    PreferenceInfo(title = "Hallo 4c", subtitle = "Hallo you")
                }
            }
        }
    }
}
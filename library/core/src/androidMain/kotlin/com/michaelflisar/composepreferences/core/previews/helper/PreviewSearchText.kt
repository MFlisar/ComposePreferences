package com.michaelflisar.composepreferences.core.previews.helper

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.filter.DefaultPreferenceFilter
import com.michaelflisar.composepreferences.core.filter.rememberDefaultPreferenceFilter

@Composable
@Preview(showBackground = true)
fun PreviewSearchText() {
    MaterialTheme {
        Surface {
            Column {
                val highlightSpan = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)
                val filter1 = rememberDefaultPreferenceFilter(
                    "ha yo",
                    mode = DefaultPreferenceFilter.Mode.ContainsText,
                    highlightSpan = highlightSpan
                )
                val filter2 = rememberDefaultPreferenceFilter(
                    "ha yo",
                    mode = DefaultPreferenceFilter.Mode.AnyWord(),
                    highlightSpan = highlightSpan
                )
                val filter3 = rememberDefaultPreferenceFilter(
                    "ha yo",
                    mode = DefaultPreferenceFilter.Mode.AllWords(false),
                    highlightSpan = highlightSpan
                )
                val filter4 = rememberDefaultPreferenceFilter(
                    "ha yo 4c",
                    mode = DefaultPreferenceFilter.Mode.AllWords(false),
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
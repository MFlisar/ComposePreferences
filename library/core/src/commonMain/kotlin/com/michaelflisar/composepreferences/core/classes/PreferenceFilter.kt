package com.michaelflisar.composepreferences.core.classes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.SpanStyle

val LocalPreferenceFilter = compositionLocalOf<PreferenceFilter?> { null }

class PreferenceFilter internal constructor(
    val search: MutableState<String>,
    val ignoreCase: Boolean,
    val mode: Mode,
    val highlightSpan: SpanStyle,
    val flattenResult : Boolean
) {
    enum class Mode {
        Exakt,
        AllWords,
        AnyWord
    }

    companion object {
        internal fun words(search: String) = search.split(" ").map { it.trim() }.filter { it.isNotEmpty() }
    }

    fun isActive() = search.value.isNotEmpty()

    fun filter(search: String, tags: List<String>): Boolean {
        if (tags.isEmpty() && search.isEmpty()) {
            return true
        }
        return when (mode) {
            Mode.Exakt -> tags.any { it.contains(search, ignoreCase) }
            Mode.AllWords,
            Mode.AnyWord -> {
                val matches = words(search).map { word ->
                    tags.any { it.contains(word, ignoreCase) }
                }
                if (mode == Mode.AllWords) {
                    !matches.contains(false)
                } else matches.contains(true)
            }
        }
    }
}

@Composable
fun rememberPreferenceFilter(
    search: String = "",
    ignoreCase: Boolean = true,
    mode: PreferenceFilter.Mode = PreferenceFilter.Mode.AllWords,
    highlightSpan: SpanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary),
    flattenResult : Boolean = false
): PreferenceFilter =
    PreferenceFilter(rememberSaveable { mutableStateOf(search) }, ignoreCase, mode, highlightSpan, flattenResult)
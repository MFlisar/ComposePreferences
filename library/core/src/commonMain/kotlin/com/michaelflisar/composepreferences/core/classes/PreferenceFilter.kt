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
    val ignoreCase: MutableState<Boolean>,
    val mode: MutableState<Mode>,
    val highlightSpan: SpanStyle,
    val flattenResult: MutableState<Boolean>
) {
    enum class Mode {
        ContainsText,
        AllWords,
        AnyWord
    }

    internal data class Word(val text: String, val count: Int)

    companion object {
        internal fun words(search: String, ignoreCase: Boolean): List<Word> {
            val splitted = search.split(" ").map { it.trim() }.filter { it.isNotEmpty() }
                .map { if (ignoreCase) it.lowercase() else it }
            return splitted
                .distinct()
                .map { w ->
                    Word(w, splitted.count { it == w })
                }
        }

    }

    fun isActive() = search.value.isNotEmpty()

    fun filter(search: String, tags: List<String>): Boolean {
        if (search.trim().isEmpty()) {
            return true
        }
        return when (mode.value) {
            Mode.ContainsText -> tags.any { it.contains(search, ignoreCase.value) }
            Mode.AllWords,
            Mode.AnyWord-> {
                val searchWords = words(search, ignoreCase.value)
                val tagWords = words(tags.joinToString(" "), ignoreCase.value)
                val matches = searchWords.map { word ->
                    tagWords.filter { it.text == word.text }.sumOf { it.count } >= word.count
                }

                if (tags.contains("Pref 1.1")) {
                    println("mode.value = ${mode.value}")
                    println("search = '$search' (isEmpty = ${search.isEmpty()})")
                    println("searchWords = $searchWords")
                    println("tagWords = $tagWords")
                    println("matches = $matches")
                }

                if (mode.value == Mode.AllWords) {
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
    mode: PreferenceFilter.Mode = PreferenceFilter.Mode.ContainsText,
    highlightSpan: SpanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary),
    flattenResult: Boolean = false
): PreferenceFilter = PreferenceFilter(
    rememberSaveable { mutableStateOf(search) },
    rememberSaveable { mutableStateOf(ignoreCase) },
    rememberSaveable { mutableStateOf(mode) },
    highlightSpan,
    rememberSaveable { mutableStateOf(flattenResult) }
)
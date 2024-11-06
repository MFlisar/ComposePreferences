package com.michaelflisar.composepreferences.core.filter

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

class DefaultPreferenceFilter internal constructor(
    override val search: MutableState<String>,
    override val flattenResult: MutableState<Boolean>,
    val mode: MutableState<Mode>,
    val ignoreCase: MutableState<Boolean>,
    val highlightSpan: SpanStyle
) : PreferenceFilter {

    sealed class Mode {
        data object ContainsText : Mode()
        data class AllWords(val fullWordsOnly: Boolean = false) : Mode()
        data class AnyWord(val fullWordOnly: Boolean = false) : Mode()

        companion object {
            val SAVER = Saver<MutableState<Mode>, Int>(
                save = {
                    when (val value = it.value) {
                        ContainsText -> 0
                        is AllWords -> if (value.fullWordsOnly) 1 else 2
                        is AnyWord -> if (value.fullWordOnly) 3 else 4
                    }
                },
                restore = {
                    val value = when (it) {
                        1 -> AllWords(true)
                        2 -> AllWords(false)
                        3 -> AnyWord(true)
                        4 -> AnyWord(false)
                        else -> ContainsText
                    }
                    mutableStateOf(value)
                }
            )
        }
    }

    override fun filter(search: String, tags: List<String>): Boolean {
        if (search.trim().isEmpty()) {
            return true
        }
        val fullWordsOnly = when (val m = mode.value) {
            is Mode.AllWords -> m.fullWordsOnly
            is Mode.AnyWord -> m.fullWordOnly
            Mode.ContainsText -> false
        }
        return when (mode.value) {
            Mode.ContainsText -> tags.any { it.contains(search, ignoreCase.value) }
            is Mode.AllWords,
            is Mode.AnyWord -> {
                val searchWords = PreferenceFilterWord.create(search, ignoreCase.value)
                val tagWords = PreferenceFilterWord.create(tags.joinToString(" "), ignoreCase.value)
                val matches = searchWords.map { word ->
                    tagWords.filter { tag ->
                        if (fullWordsOnly) {
                            tag.text == word.text
                        } else {
                            tag.text.contains(word.text)
                        }
                    }.sumOf { it.count } >= word.count
                }
                if (mode.value is Mode.AllWords) {
                    !matches.contains(false)
                } else matches.contains(true)
            }
        }
    }

    override fun highlight(text: String): AnnotatedString {
        return buildAnnotatedString {
            append(text)
            val parts = when (mode.value) {
                Mode.ContainsText -> listOf(search.value)
                is Mode.AllWords,
                is Mode.AnyWord -> PreferenceFilterWord.create(search.value, ignoreCase.value).map { it.text }
            }.filter { it.isNotEmpty() }
            for (part in parts) {
                var offset = 0
                var startIndex = 0
                while (text.indexOf(part, startIndex = offset, ignoreCase = ignoreCase.value)
                        .also { startIndex = it } != -1
                ) {
                    val endIndex = startIndex + part.length
                    addStyle(style = highlightSpan, start = startIndex, end = endIndex)
                    offset = endIndex
                }
            }
        }
    }
}

@Composable
fun rememberDefaultPreferenceFilter(
    search: String = "",
    flattenResult: Boolean = false,
    mode: DefaultPreferenceFilter.Mode = DefaultPreferenceFilter.Mode.ContainsText,
    ignoreCase: Boolean = true,
    highlightSpan: SpanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
): DefaultPreferenceFilter = DefaultPreferenceFilter(
    rememberSaveable { mutableStateOf(search) },
    rememberSaveable { mutableStateOf(flattenResult) },
    rememberSaveable(saver = DefaultPreferenceFilter.Mode.SAVER) { mutableStateOf(mode) },
    rememberSaveable { mutableStateOf(ignoreCase) },
    highlightSpan
)
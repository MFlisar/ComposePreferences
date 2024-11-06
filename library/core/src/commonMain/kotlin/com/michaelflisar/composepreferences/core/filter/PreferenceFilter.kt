package com.michaelflisar.composepreferences.core.filter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.AnnotatedString

val LocalPreferenceFilter = compositionLocalOf<PreferenceFilter?> { null }

interface PreferenceFilter {

    val search: MutableState<String>
    val flattenResult: MutableState<Boolean>

    fun isActive() = search.value.isNotEmpty()

    fun filter(search: String, tags: List<String>): Boolean
    fun highlight(text: String): AnnotatedString
}
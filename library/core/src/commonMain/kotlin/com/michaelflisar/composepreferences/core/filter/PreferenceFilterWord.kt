package com.michaelflisar.composepreferences.core.filter

internal class PreferenceFilterWord private constructor(
    val text: String,
    val count: Int
) {
    companion object {
        fun create(search: String, ignoreCase: Boolean): List<PreferenceFilterWord> {
            val splitted = search.split(" ").map { it.trim() }.filter { it.isNotEmpty() }
                .map { if (ignoreCase) it.lowercase() else it }
            return splitted
                .distinct()
                .map { w ->
                    PreferenceFilterWord(w, splitted.count { it == w })
                }
        }
    }

    override fun toString(): String {
        return "{text = $text, count = $count}"
    }
}
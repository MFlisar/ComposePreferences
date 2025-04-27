package com.michaelflisar.composepreferences.core.classes

import com.michaelflisar.composepreferences.core.PreferenceSection

sealed class PreferenceType {
    data object Item : PreferenceType()
    class Section(
        val expandable: PreferenceSection.Expandable
    ) : PreferenceType()
    data object Group : PreferenceType()
}
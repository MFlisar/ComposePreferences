package com.michaelflisar.composepreferences.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceSettingsDefaults
import com.michaelflisar.composepreferences.core.classes.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.core.hierarchy.HierarchyAwareRoot
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceRootScope

@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    scrollable: Boolean = true,
    settings: PreferenceSettings = PreferenceSettingsDefaults.settings(),
    content: @Composable PreferenceRootScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalPreferenceSettings provides settings
    ) {
        HierarchyAwareRoot(
            scrollable = scrollable,
            modifier = modifier
        ) {
            content()
        }
    }
}
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

/**
 * the **root** screen holding preference items
 *
 * @param modifier the [Modifier] for this composable
 * @param scrollable if true, this composable does wrap its content inside a scrollable container
 * @param settings the [PreferenceSettings] for this screen - use [PreferenceSettingsDefaults.settings] to provide your own settings
 * @param content the content of this screen
 */
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
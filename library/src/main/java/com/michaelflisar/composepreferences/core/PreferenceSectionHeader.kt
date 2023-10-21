package com.michaelflisar.composepreferences.core

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.classes.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

@Composable
fun PreferenceScope.PreferenceSectionHeader(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = PreferenceStyleDefaults.header()
) {
    BasePreference(
        setup = PreferenceItemSetup(ignoreForceNoIconInset = true),
        enabled = enabled,
        visible = visible,
        title = {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.titleMedium
            ) {
                title()
            }
        },
        subtitle = subtitle,
        icon = icon,
        preferenceStyle = preferenceStyle
    )
}
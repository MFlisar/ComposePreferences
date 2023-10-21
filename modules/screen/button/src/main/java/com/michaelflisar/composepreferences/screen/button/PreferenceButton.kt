package com.michaelflisar.composepreferences.screen.button

import androidx.compose.runtime.Composable
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

@Composable
fun PreferenceScope.PreferenceButton(
    // Special
    onClick: (() -> Unit),
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    BasePreference(
        enabled = enabled,
        visible = visible,
        title = title,
        subtitle = subtitle,
        icon = icon,
        onClick = onClick,
        preferenceStyle = preferenceStyle,
        content = null
    )
}


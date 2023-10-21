package com.michaelflisar.composepreferences.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

@Composable
fun PreferenceScope.PreferenceInfo(
    // Special
    onLongClick: (() -> Unit)? = null,
    ignoreMinItemHeight: Boolean = false,
    alignment: Alignment.Vertical = Alignment.CenterVertically,
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    BasePreference(
        setup = PreferenceItemSetup(ignoreMinItemHeight = ignoreMinItemHeight, alignment = alignment),
        enabled = enabled,
        visible = visible,
        title = title,
        subtitle = subtitle,
        icon = icon,
        onLongClick = onLongClick,
        preferenceStyle = preferenceStyle,
        content = null
    )
}


package com.michaelflisar.composepreferences.core

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.composables.BasePreferenceContainer
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

@Composable
fun PreferenceScope.PreferenceDivider(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled
) {
    BasePreferenceContainer(
        modifier = Modifier,
        enabled = enabled,
        visible = visible,
        onClick = null,
        preferenceStyle = LocalPreferenceSettings.current.itemStyle
    ) { modifier ->
        Divider(modifier = modifier)
    }
}
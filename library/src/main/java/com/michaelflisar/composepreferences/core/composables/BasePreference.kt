package com.michaelflisar.composepreferences.core.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

@Composable
fun PreferenceScope.BasePreference(
    modifier: Modifier = Modifier,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    setup: PreferenceItemSetup = PreferenceItemSetup(),
    content: (@Composable ColumnScope.() -> Unit)? = null
) {
    BasePreferenceContainer(
        modifier = modifier,
        enabled = enabled,
        visible = visible,
        onClick = onClick,
        onLongClick = onLongClick,
        group = setup.group,
        preferenceStyle = preferenceStyle
    ) { modifier ->
        PreferenceItem(
            modifier = modifier,
            preferenceStyle = preferenceStyle,
            setup = setup,
            headline = title,
            subHeadline = subtitle,
            leading = icon,
            content = content
        )
    }
}


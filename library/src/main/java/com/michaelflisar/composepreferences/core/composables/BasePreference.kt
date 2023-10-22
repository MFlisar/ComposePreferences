package com.michaelflisar.composepreferences.core.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.classes.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

/**
 * this is the default root composable that uses a [BasePreferenceContainer] and provides you with
 * a common default layout that consists of an area for an icon, a title, a sub title and some content
 *
 * &nbsp;
 *
 * In most cases this should be the root container for any custom preference.
 *
 * @param modifier the [Modifier] for this composable
 * @param enabled the [Dependency] that from which the enabled state is derived from
 * @param visible the [Dependency] that from which the visibility state is derived from
 * @param title the content of the title area
 * @param subtitle the content of the subtitle area
 * @param icon the content of the icon area
 * @param onClick the optional click listener for this item
 * @param onLongClick the optional long click listener for this item
 * @param preferenceStyle the [PreferenceStyle] of this this item - use [PreferenceStyleDefaults.item] and all the predefined styles in [PreferenceStyleDefaults] to provide your own style
 * @param setup the [PreferenceItemSetup] of this this item which allows you to influence some of the behaviour and style specifically for a special item type
 * @param content the content of this composable
 */
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


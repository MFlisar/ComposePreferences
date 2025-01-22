package com.michaelflisar.composepreferences.screen.button

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import com.michaelflisar.composepreferences.core.PreferenceInfoDefaults
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.helper.SearchText
import com.michaelflisar.composepreferences.core.internal.rememberPreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope

/* --8<-- [start: constructor] */
/**
 * A button preference item - this item simply executes an action on click
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param onClick the click callback of this item
 */
@Composable
fun PreferenceScope.PreferenceButton(
    // Special
    onClick: (() -> Unit),
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList()
)
/* --8<-- [start: constructor] */
{
    BasePreference(
        enabled = enabled,
        visible = visible,
        title = title,
        subtitle = subtitle,
        icon = icon,
        onClick = onClick,
        itemStyle = itemStyle,
        titleRenderer = titleRenderer,
        subtitleRenderer = subtitleRenderer,
        filterTags = filterTags,
        content = null
    )
}
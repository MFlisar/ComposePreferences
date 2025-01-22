package com.michaelflisar.composepreferences.core

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.helper.SearchText
import com.michaelflisar.composepreferences.core.internal.LocalParent
import com.michaelflisar.composepreferences.core.internal.rememberPreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceGroupScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceRootScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceSectionScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceSectionScopeInstance
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle

/* --8<-- [start: constructor] */
/**
 * A section preference item
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 */
@Composable
fun PreferenceRootScope.PreferenceSection(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: String? = null,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultSectionItemStyle,
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    content: @Composable PreferenceSectionScope.() -> Unit
)
/* --8<-- [end: constructor] */
{
    PreferenceSectionImpl(
        enabled,
        visible,
        title,
        subtitle,
        icon,
        itemStyle,
        titleRenderer,
        subtitleRenderer,
        filterTags
    ) {
        PreferenceSectionScopeInstance.content()
    }
}

/* --8<-- [start: constructor2] */
/**
 * A section preference item
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 */
@Composable
fun PreferenceGroupScope.PreferenceSection(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: String? = null,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultSectionItemStyle,
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    content: @Composable PreferenceSectionScope.() -> Unit
)
/* --8<-- [end: constructor2] */
{
    PreferenceSectionImpl(
        enabled,
        visible,
        title,
        subtitle,
        icon,
        itemStyle,
        titleRenderer,
        subtitleRenderer,
        filterTags
    ) {
        PreferenceSectionScopeInstance.content()
    }
}

@Composable
private fun PreferenceScope.PreferenceSectionImpl(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: String?,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultSectionItemStyle,
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    content: @Composable () -> Unit
) {
    val tags = filterTags + listOfNotNull(title, subtitle)
    val item = rememberPreferenceItemState(PreferenceType.Section, visible, tags, false)

    LaunchedEffect(filterTags, item.children.value.map { it.tags }) {
        item.allTags.value = tags + item.children.value.map { it.tags }.flatten()
    }

    if (title != null || subtitle != null) {
        BasePreference(
            itemSetup = PreferenceItemSetup(ignoreForceNoIconInset = true, minHeight = 0.dp),
            enabled = enabled,
            visible = visible,
            title = { SearchText(title ?: "", titleRenderer) },
            subtitle = { subtitle?.let { SearchText(subtitle, subtitleRenderer) } },
            icon = icon,
            itemStyle = itemStyle,
            filterTags = tags,
            item = item
        )
    }
    CompositionLocalProvider(LocalParent provides item) {
        content()
    }
}
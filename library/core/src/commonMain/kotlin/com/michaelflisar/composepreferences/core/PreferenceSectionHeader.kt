package com.michaelflisar.composepreferences.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.helper.SearchText
import com.michaelflisar.composepreferences.core.internal.LocalParent
import com.michaelflisar.composepreferences.core.internal.rememberPreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceGroupScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceGroupScopeInstance
import com.michaelflisar.composepreferences.core.scopes.PreferenceRootScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceSectionScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceSectionScopeInstance
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle

@Composable
fun PreferenceRootScope.PreferenceSectionHeader(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: String,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultSectionItemStyle,
    filterTags: List<String> = emptyList(),
    content: @Composable PreferenceSectionScope.() -> Unit
) {
    PreferenceSectionHeaderImpl(
        enabled,
        visible,
        title,
        subtitle,
        icon,
        itemStyle,
        filterTags
    ) {
        PreferenceSectionScopeInstance.content()
    }
}

@Composable
fun PreferenceGroupScope.PreferenceSectionHeader(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: String,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultSectionItemStyle,
    filterTags: List<String> = emptyList(),
    content: @Composable PreferenceSectionScope.() -> Unit
) {
    PreferenceSectionHeaderImpl(
        enabled,
        visible,
        title,
        subtitle,
        icon,
        itemStyle,
        filterTags
    ) {
        PreferenceSectionScopeInstance.content()
    }
}

@Composable
private fun PreferenceScope.PreferenceSectionHeaderImpl(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: String,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultSectionItemStyle,
    filterTags: List<String> = emptyList(),
    content: @Composable () -> Unit
) {
    val tags = filterTags + listOfNotNull(title, subtitle)
    val item = rememberPreferenceItemState(PreferenceType.Section, visible, tags)

    LaunchedEffect(filterTags, item.children.value.map { it.tags }) {
        item.allTags.value = tags + item.children.value.map { it.tags }.flatten()
    }

    BasePreference(
        itemSetup = PreferenceItemSetup(ignoreForceNoIconInset = true),
        enabled = enabled,
        visible = visible,
        title = {
            SearchText(title)
        },
        subtitle = { subtitle?.let { SearchText(subtitle) } },
        icon = icon,
        itemStyle = itemStyle,
        filterTags = tags,
        item = item
    )
    CompositionLocalProvider(LocalParent provides item) {
        content()
    }
}
package com.michaelflisar.composepreferences.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSettings
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.helper.SearchText
import com.michaelflisar.composepreferences.core.internal.LocalParent
import com.michaelflisar.composepreferences.core.internal.LocalState
import com.michaelflisar.composepreferences.core.internal.rememberPreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceGroupScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceGroupScopeInstance
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle

@Composable
fun PreferenceScope.PreferenceSubScreen(
    // Special
    // Base Preference
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    title: String,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultGroupItemStyle,
    filterTags: List<String> = emptyList(),
    content: @Composable PreferenceGroupScope.() -> Unit
) {
    val tags = filterTags + listOfNotNull(title, subtitle)
    val item = rememberPreferenceItemState(PreferenceType.Group, visible, tags)

    LaunchedEffect(filterTags, item.getChildren(true).map { it.tags }) {
        item.allTags.value = tags + item.getChildren(true).map { it.tags }.flatten()
    }

    val state = LocalState.current
    val preferenceSettings = LocalPreferenceSettings.current

    // Item (Group)
    BasePreference(
        itemSetup = PreferenceItemSetup(
            trailingContentSize = PreferenceItemSetupDefaults.trailingContentSize(0.dp)
        ),
        enabled = enabled,
        visible = visible,
        title = {
            SearchText(title)
        },
        subtitle = subtitle?.let { { SearchText((it)) } },
        icon = icon,
        itemStyle = itemStyle,
        onClick = {
            //println("openedGroups: add index: ${index.value} | localIndex = $localIndex")
            state.openedGroups.add(item.id)
        },
        item = item,
        content = if (preferenceSettings.subScreenEndIndicator == null) {
            null
        } else {
            {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }
    )

    // Content
    CompositionLocalProvider(
        LocalParent provides item
    ) {
        PreferenceGroupScopeInstance.content()
    }
}
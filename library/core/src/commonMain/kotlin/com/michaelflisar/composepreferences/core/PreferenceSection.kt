package com.michaelflisar.composepreferences.core

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import com.michaelflisar.composepreferences.core.scopes.PreferenceSectionScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceSectionScopeInstance
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle
import kotlin.math.exp

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
fun PreferenceGroupScope.PreferenceSection(
    // Special
    expandable: PreferenceSection.Expandable = PreferenceSection.Expandable.Disabled,
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
    content: @Composable PreferenceSectionScope.() -> Unit,
)
/* --8<-- [end: constructor] */ {
    val tags = filterTags + listOfNotNull(title, subtitle)
    val item = rememberPreferenceItemState(PreferenceType.Section(expandable), visible, tags, false)

    LaunchedEffect(filterTags, item.children.value.map { it.tags }) {
        item.allTags.value = tags + item.children.value.map { it.tags }.flatten()
    }

    if (title != null || subtitle != null || expandable is PreferenceSection.Expandable.Enabled) {
        BasePreference(
            itemSetup = PreferenceItemSetup(ignoreForceNoIconInset = true, minHeight = 0.dp),
            enabled = enabled,
            visible = visible,
            title = {
                if (expandable is PreferenceSection.Expandable.Enabled) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            SearchText(title ?: "", titleRenderer)
                        }
                        val rot by animateFloatAsState(if (expandable.state.isExpanded()) 180f else 0f)
                        Icon(
                            modifier = Modifier.rotate(rot),
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                } else {
                    SearchText(title ?: "", titleRenderer)
                }
            },
            subtitle = { subtitle?.let { SearchText(subtitle, subtitleRenderer) } },
            icon = icon,
            itemStyle = itemStyle,
            filterTags = tags,
            item = item,
            onClick = if (expandable is PreferenceSection.Expandable.Enabled) {
                { expandable.state.toggleExpand() }
            } else null
        )
    }
    CompositionLocalProvider(LocalParent provides item) {
        PreferenceSectionScopeInstance.content()
    }
}

@Composable
fun rememberPreferenceSectionExpandedState(
    expanded: Boolean = true,
): PreferenceSection.Expandable.Enabled {
    val expanded = remember { mutableStateOf(expanded) }
    return PreferenceSection.Expandable.Enabled(
        state = PreferenceSection.ExpandableState(
            expanded = { expanded.value },
            toggle = { expanded.value = !expanded.value }
        )
    )
}

@Composable
fun rememberPreferenceSectionSharedExpandedState(
    expandedIds: List<Int> = emptyList(),
    expandSingleOnly: Boolean = false
): PreferenceSection.SharedExpandState {

    val expanded = remember { expandedIds.toMutableStateList() }
    val expandSingleOnly = remember(expandSingleOnly) { mutableStateOf(expandSingleOnly) }
    LaunchedEffect(expandSingleOnly.value) {
        if (expandSingleOnly.value) {
            if (expanded.size > 1) {
                val first = expanded.get(0)
                expanded.clear()
                expanded.add(first)
            }
        }
    }
    val toggleExpand = { id: Int ->
        if (expandSingleOnly.value) {
            val select = !expanded.contains(id)
            expanded.clear()
            if (select)
                expanded.add(id)
        } else {
            if (expanded.contains(id)) expanded.remove(id) else expanded.add(id)
        }
        Unit
    }

    return PreferenceSection.SharedExpandState(expanded, expandSingleOnly, toggleExpand)
}

object PreferenceSection {

    sealed class Expandable {
        data object Disabled : Expandable()

        data class Enabled(
            val state: ExpandState
        ) : Expandable()
    }

    interface ExpandState {
        fun isExpanded(): Boolean
        fun toggleExpand()
    }

    class ExpandableState(
        private val expanded: () -> Boolean,
        private val toggle: () -> Unit
    ) : ExpandState {
        override fun isExpanded() = expanded()
        override fun toggleExpand()  {
            this.toggle()
        }
    }

    class SharedExpandableState(
        val id: Int,
        val state: SharedExpandState
    ) : ExpandState {
        override fun isExpanded()= state.expanded.contains(id)
        override fun toggleExpand() { state.toggleExpand(id) }
    }

    class SharedExpandState(
        val expanded: SnapshotStateList<Int>,
        val expandSingleOnly: MutableState<Boolean>,
        val toggleExpand: (id: Int) -> Unit
    ) {
        fun toExpandableEnabled(id: Int): Expandable.Enabled {
            return Expandable.Enabled(
                SharedExpandableState(id, this)
            )
        }
    }

}
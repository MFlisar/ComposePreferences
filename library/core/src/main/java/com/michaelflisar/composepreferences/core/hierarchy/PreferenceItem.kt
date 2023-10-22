package com.michaelflisar.composepreferences.core.hierarchy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
internal fun PreferenceScope.PreferenceItem(
    group: Boolean = false,
    content: @Composable PreferenceItemScopeInstance.() -> Unit
) {
    val openedGroupIndizes = LocalOpenedGroups.current.toList()
    val openedGroupLevel = openedGroupIndizes.size - 1

    val level = LocalLevel.current.level
    val parents = LocalParents.current.ids
    val index = LocalIndex.current.index

    val hierarchyData = HierarchyData(parents, index)

    val visible by remember(level, openedGroupLevel, openedGroupIndizes) {
        derivedStateOf {
            level == openedGroupLevel + 1 && openedGroupIndizes == parents
        }
    }

    println("visible = $visible | group = $group | level = $level | parents = $parents | index = $index | openedGroupLevel = $openedGroupLevel | openedGroupIndizes = $openedGroupIndizes")

    if (visible) {
        PreferenceItemScopeInstance(hierarchyData).content()
    }
    if (!group)
        LocalIndex.current.index++
}
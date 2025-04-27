package com.michaelflisar.composepreferences.core.internal

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.michaelflisar.composepreferences.core.PreferenceSection
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.filter.LocalPreferenceFilter
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup

sealed class PreferenceItemState {

    abstract val id: Int
    abstract val parent: PreferenceItemState?
    abstract val children: MutableState<List<Item>>
    abstract val visible: State<Boolean>
    abstract val visibleTransitionState: MutableTransitionState<Boolean>
    abstract val tags: List<String>
    abstract val allTags: MutableState<List<String>>

    fun getChildren(recursive: Boolean): List<Item> = if (recursive) {
        children.value + children.value.map { it.getChildren(true) }.flatten()
    } else children.value

    private fun getAllIDs(includingSelf: Boolean, recursive: Boolean = true) =
        (if (includingSelf) listOf(id) else emptyList()) +
                getChildren(recursive).map { it.id }

    fun containsID(id: Int, recursive: Boolean = true) = getAllIDs(false, recursive).contains(id)

    fun getLevel(id: Int, children: List<Item>): Int {
        var index = 0
        if (children.find { it.id == id } != null) {
            index++
        } else {
            val subs = children.first { it.containsID(id) }.children.value
            index += getLevel(id, subs)
        }
        return index
    }

    fun getLevel() : Int {
        var lvl = 0
        var tmp: PreferenceItemState? = parent
        while (tmp != null && tmp !is Root) {
            if (tmp is Item && tmp.type == PreferenceType.Group) {
                lvl++
            }
            tmp = tmp.parent
        }
        //println("item = ${this.tags} | lvl = $lvl | type = ${(this as? Item)?.type} | parentType = ${(this.parent as? Item)?.type}")
        return lvl
    }

    data class Root(
        override val children: MutableState<List<Item>> = mutableStateOf(emptyList())
    ) : PreferenceItemState() {
        override val id = 0
        override val visible: State<Boolean> = mutableStateOf(true)
        override val visibleTransitionState = MutableTransitionState(true)
        override val tags = emptyList<String>()
        override val allTags: MutableState<List<String>> = mutableStateOf(emptyList())
        override val parent: PreferenceItemState? = null

        fun getLevel(id: Int) = getLevel(id, children.value)
    }

    data class Item(
        override val id: Int,
        override val parent: PreferenceItemState,
        val type: PreferenceType,
        override val tags: List<String>,
        override val allTags: MutableState<List<String>>,
        override val children: MutableState<List<Item>>,
        override val visible: State<Boolean>,
        override val visibleTransitionState: MutableTransitionState<Boolean>,
        val excludeFromSectionStyle: Boolean
    ) : PreferenceItemState()
}

@Composable
fun rememberPreferenceItemState(
    type: PreferenceType,
    visible: Dependency,
    tags: List<String>,
    excludeFromSectionStyle: Boolean
): PreferenceItemState.Item {

    val state = LocalState.current
    val parent = LocalParent.current
    val id by remember { mutableIntStateOf(state.getNextID()) }

    var item = parent.children.value.find { it.id == id }

    if (item == null) {
        //println("item = created - id = $id | parent = ${parent.id}")
        val stateVisible = visible.state()
        val filter = LocalPreferenceFilter.current

        val children = remember { mutableStateOf<List<PreferenceItemState.Item>>(emptyList()) }
        val allTags = remember { mutableStateOf<List<String>>(emptyList()) }

        val visible = remember {
            derivedStateOf {

                var group: PreferenceItemState? = parent
                while (group !is PreferenceItemState.Root && (group is PreferenceItemState.Item && group.type != PreferenceType.Group)) {
                    group = group.parent
                }
                val groupVisible = if (filter?.isActive() == true && filter.flattenResult.value) {
                    type == PreferenceType.Item
                } else if (state.opened.isEmpty()) {
                    group is PreferenceItemState.Root
                } else {
                    group?.id == state.opened.lastOrNull()?.id
                }

                val section = (parent as? PreferenceItemState.Item)?.type as? PreferenceType.Section
                val sectionExpandableState = section?.expandable as? PreferenceSection.Expandable.Enabled
                var sectionExpanded = true
                if (sectionExpandableState != null) {
                    sectionExpanded = sectionExpandableState.state.isExpanded()
                }

                //println("item = id = $id | groupVisible = $groupVisible | groupVisible = ${stateVisible.value}")

                groupVisible && sectionExpanded &&
                        (filter?.filter(filter.search.value, allTags.value) ?: true) &&
                        stateVisible.value
            }
        }

        val visibleTransitionState = remember { MutableTransitionState(visible.value) }
        item = remember {
            PreferenceItemState.Item(
                id = id,
                parent = parent,
                type = type,
                tags = tags,
                children = children,
                allTags = allTags,
                visible = visible,
                visibleTransitionState = visibleTransitionState,
                excludeFromSectionStyle = excludeFromSectionStyle
            ).also {
                parent.children.value =
                    parent.children.value.toMutableList().also { list -> list.add(it) }
            }
        }

    }

    LaunchedEffect(item.visible.value) {
        item.visibleTransitionState.targetState = item.visible.value
        //println("ANIMATION: item = id = $id | visible = ${visible.value} | target = ${visibleTransitionState.targetState}")
    }

    return item
}
package com.michaelflisar.composepreferences.core

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceSettingsDefaults
import com.michaelflisar.composepreferences.core.classes.PreferenceState
import com.michaelflisar.composepreferences.core.classes.rememberPreferenceState
import com.michaelflisar.composepreferences.core.filter.LocalPreferenceFilter
import com.michaelflisar.composepreferences.core.filter.PreferenceFilter
import com.michaelflisar.composepreferences.core.internal.LocalParent
import com.michaelflisar.composepreferences.core.internal.LocalState
import com.michaelflisar.composepreferences.core.internal.PreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceRootScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceRootScopeInstance

/* --8<-- [start: constructor] */
/**
 * the **root** screen holding preference items
 *
 * @param modifier the [Modifier] for this composable
 * @param scrollable if true, this composable does wrap its content inside a scrollable container
 * @param settings the [PreferenceSettings] for this screen - use [PreferenceSettingsDefaults.settings] to provide your own settings
 * @param filter the [PreferenceFilter] for this screen - use [rememberDefaultPreferenceFilter] to use some of the predefined options or provide your own [PreferenceFilter] implementation
 * @param content the content of this screen
 */
@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    scrollable: Boolean = true,
    settings: PreferenceSettings = PreferenceSettingsDefaults.settings(),
    filter: PreferenceFilter? = null,
    state: PreferenceState = rememberPreferenceState(),
    content: @Composable PreferenceRootScope.() -> Unit
)
/* --8<-- [end: constructor] */
{
    val children = remember { mutableStateOf<List<PreferenceItemState.Item>>(emptyList()) }
    val root = remember { PreferenceItemState.Root(children) }

    BackHandler(state.openedGroups.size > 0) {
        //println("BACK - state.openedGroups = ${state.openedGroups.size}")
        //state.popLast()
        state.openedGroups.removeAt(state.openedGroups.lastIndex)
    }

    val scrollStates = rememberSaveable(saver = listSaver(
        save = { it.toList() },
        restore = { it.toMutableStateList() }
    )) { listOf(0).toMutableStateList() }
    val scrollStateToUpdate = rememberSaveable { mutableStateOf<Int?>(null) }
    val scrollState = remember { mutableStateOf(ScrollState(0)) }
    LaunchedEffect(state.openedGroups.size) {
        scrollStateToUpdate.value = null
        scrollState.value.animateScrollTo(0)
    }

    CompositionLocalProvider(
        LocalPreferenceSettings provides settings,
        LocalPreferenceFilter provides filter,
        LocalParent provides root,
        LocalState provides state
    ) {
        LaunchedEffect(
            root.getChildren(true).map { it.visible.value }
        ) {
            val list = root.getChildren(true)

            // whenever all visible items have finished their animation
            snapshotFlow {
                list.filter { it.visible.value }
                    .map { it.visibleTransitionState.isIdle && it.visibleTransitionState.targetState }
            }.collect {
                //if (it.all { it }) {
                // whenever items change because of the animation we try to restore the scroll state...
                // this gives us a quite good result
                var lastOffset = scrollStates.getOrNull(state.openedGroups.size)
                if (lastOffset != null) {
                    scrollState.value.animateScrollTo(lastOffset)
                }
                if (it.all { it }) {
                    // just to make sure, maybe something leads to opening levels faster than this is executed...
                    // => we make sure scrollstate for all but the current level exists
                    while (scrollStates.lastIndex < state.openedGroups.size) {
                        scrollStates.add(0)
                    }
                    if (lastOffset == null) {
                        lastOffset = scrollState.value.value
                        scrollStates.add(lastOffset)
                    }
                    while (scrollStates.lastIndex > state.openedGroups.size) {
                        scrollStates.removeAt(scrollStates.lastIndex)
                    }
                    scrollStateToUpdate.value = state.openedGroups.size
                    //println("scroll state reset - level: ${state.openedGroups.size} | items: ${it.size} | scrollstate: ${scrollState.value} - ${scrollState.value.value}")
                }
            }
        }

        LaunchedEffect(scrollState.value.value) {
            val index = scrollStateToUpdate.value
            //println("scrolled to ${scrollState.value.value} | index = $index")
            if (index != null) {
                scrollStates[index] = scrollState.value.value
            }
        }

        Column(
            modifier = Modifier
                .then(
                    if (scrollable) {
                        Modifier.verticalScroll(scrollState.value)
                    } else Modifier
                ).then(modifier)
        ) {
            PreferenceRootScopeInstance.content()
        }
    }
}

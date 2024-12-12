package com.michaelflisar.composepreferences.core

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import com.michaelflisar.composepreferences.core.filter.LocalPreferenceFilter
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceSettingsDefaults
import com.michaelflisar.composepreferences.core.classes.PreferenceState
import com.michaelflisar.composepreferences.core.classes.rememberPreferenceState
import com.michaelflisar.composepreferences.core.filter.PreferenceFilter
import com.michaelflisar.composepreferences.core.internal.LocalParent
import com.michaelflisar.composepreferences.core.internal.LocalState
import com.michaelflisar.composepreferences.core.internal.PreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceRootScope
import com.michaelflisar.composepreferences.core.scopes.PreferenceRootScopeInstance

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
) {
    val children = remember { mutableStateOf<List<PreferenceItemState.Item>>(emptyList()) }
    val root = remember { PreferenceItemState.Root(children) }

    LaunchedEffect(root.getChildren(true).map { it.visible.value }) {
        val list = root.getChildren(true)
        state.items.value =
            list.map { PreferenceState.Item(it.id, it.getLevel(), it.type, it.visible.value) }
    }

    BackHandler(state.openedGroups.size > 0) {
        //println("BACK - state.openedGroups = ${state.openedGroups.size}")
        //state.popLast()
        state.openedGroups.removeAt(state.openedGroups.lastIndex)
    }

    val scrollStates = rememberScrollStates()

    CompositionLocalProvider(
        LocalPreferenceSettings provides settings,
        LocalPreferenceFilter provides filter,
        LocalParent provides root,
        LocalState provides state
    ) {
        val scrollState: State<ScrollState> = if (Test.useScrollStateRestoration) {
            remember(state.openedGroups.size) {
                derivedStateOf {
                    while (scrollStates.size > state.openedGroups.size + 1) {
                        scrollStates.removeAt(scrollStates.lastIndex)
                    }
                    while (scrollStates.size < state.openedGroups.size + 1) {
                        //println("scroll state ADDED")
                        scrollStates.add(ScrollState(0))
                    }
                    scrollStates.last()
                }
            }
        } else {
            remember { mutableStateOf(ScrollState(0)) }
        }

        LaunchedEffect(scrollState) {
            val value = scrollState.value
            //println("scroll - value = $value | scrollState = $scrollState")
        }
        LaunchedEffect(scrollState.value) {
            //println("scrolled to ${scrollState.value} | scrollState = $scrollState")
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

@Composable
private fun rememberScrollStates(): SnapshotStateList<ScrollState> {
    return rememberSaveable(saver = listSaver(
        save = { it.toList().map { it.value } },
        restore = { it.map { ScrollState(it) }.toMutableStateList() }
    )) {
        mutableStateListOf()
    }
}
package com.michaelflisar.composepreferences.core.hierarchy

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings

internal data class Level(val level: Int = 0)

internal val LocalLevel = compositionLocalOf { Level() }

internal data class Parents(val ids: List<Int> = emptyList())

internal val LocalParents = compositionLocalOf { Parents() }

internal data class Index(var index: Int = 0)

internal val LocalIndex = compositionLocalOf { Index() }

internal val LocalOpenedGroups = compositionLocalOf { mutableListOf<Int>() }

@Composable
internal fun rememberOpenedGroups(): SnapshotStateList<Int> {
    return rememberSaveable(saver = listSaver(
        save = { it.toList() },
        restore = { it.toMutableStateList() }
    )) {
        mutableStateListOf()
    }
}

@Composable
internal fun rememberScrollStates(): SnapshotStateList<ScrollState> {
    return rememberSaveable(saver = listSaver(
        save = { it.toList().map { it.value } },
        restore = { it.map { ScrollState(it) }.toMutableStateList() }
    )) {
        mutableStateListOf()
    }
}

@LayoutScopeMarker
@Immutable
interface PreferenceItemScope {
    val data: HierarchyData
}

internal class PreferenceItemScopeInstance(override val data: HierarchyData) : PreferenceItemScope

@LayoutScopeMarker
@Immutable
interface PreferenceScope
internal object PreferenceScopeInstance : PreferenceScope

@LayoutScopeMarker
@Immutable
interface PreferenceRootScope : PreferenceScope
internal object PreferenceRootScopeInstance : PreferenceRootScope

@Composable
internal fun HierarchyAwareRoot(
    scrollable: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable PreferenceRootScope.() -> Unit
) {
    val openedGroup = rememberOpenedGroups()

    val settings = LocalPreferenceSettings.current
    LaunchedEffect(openedGroup.size) {
        settings.level.value = openedGroup.size
    }

    BackHandler(openedGroup.size > 0) {
        openedGroup.removeLast()
    }

    val scrollState = if (scrollable) {
        val scrollStates = rememberScrollStates()
        while (scrollStates.size > openedGroup.size + 1) {
            scrollStates.removeLast()
        }

        if (scrollStates.size == openedGroup.size + 1) {
            scrollStates.last()
        } else scrollStates.add(ScrollState(0)).let {
            scrollStates.last()
        }
    } else null

    CompositionLocalProvider(
        LocalLevel provides Level(),
        LocalParents provides Parents(),
        LocalIndex provides Index(0),
        LocalOpenedGroups provides openedGroup
    ) {

        Column(
            modifier = modifier
                .then(
                    if (scrollable) {
                        Modifier.verticalScroll(scrollState!!)
                    } else Modifier
                )
        ) {
            PreferenceRootScopeInstance.content()
        }
    }
}
package com.michaelflisar.composepreferences.core.hierarchy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun PreferenceScope.PreferenceGroupItem(
    item: @Composable PreferenceScope.() -> Unit,
    content: @Composable PreferenceScope.() -> Unit
) {
    val level = LocalLevel.current.level
    val parents = LocalParents.current.ids
    val index = LocalIndex.current.index

    item()

    CompositionLocalProvider(
        LocalLevel provides Level(level.inc()),
        LocalParents provides Parents(parents + index),
        LocalIndex provides Index(0),
    ) {
        PreferenceScopeInstance.content()
    }

    LocalIndex.current.index++
}
package com.michaelflisar.composepreferences.core.internal

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.michaelflisar.composepreferences.core.classes.PreferenceState

// hierarchical data
internal val LocalParent = compositionLocalOf<PreferenceItemState> { PreferenceItemState.Root() }
internal val LocalState = compositionLocalOf { PreferenceState() }
internal val LocalItem = compositionLocalOf { mutableStateOf<PreferenceItemState>(PreferenceItemState.Root()) }

//internal val LocalItemTitle = compositionLocalOf { mutableStateOf("") }
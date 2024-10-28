package com.michaelflisar.composepreferences.core.internal

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.michaelflisar.composepreferences.core.classes.PreferenceState

// hierarchical data
val LocalHierarchy = compositionLocalOf { PreferenceItemState.Root() }
val LocalParent = compositionLocalOf<PreferenceItemState> { PreferenceItemState.Root() }
val LocalState = compositionLocalOf { PreferenceState() }
val LocalItem = compositionLocalOf { mutableStateOf<PreferenceItemState>(PreferenceItemState.Root()) }

//internal val LocalItemTitle = compositionLocalOf { mutableStateOf("") }
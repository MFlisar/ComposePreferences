package com.michaelflisar.composepreferences.kotpreferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * TODO:
 *  this uses caching (disabled by default) and a blocking read => not optimal
 * this is needed so that a preference screen does initially already know its visible and enabled states =>
 * otherwise each setting would evalute this data step by step which does not lead to a visually appealing result...
 *
 * Maybe I find a better solution?
 */
@Composable
fun <T> StorageSetting<T>.collectSetting(
    initialValue: T = getCached() ?: value
): State<T> {
    return flow.collectAsState(initial = initialValue)
}

/**
 simple extension function to plug in a setting as preference data provider and updater
 */
@Composable
fun <T> StorageSetting<T>.asPreferenceData(): PreferenceData<T> {
    val scope = rememberCoroutineScope()
    val state = collectSetting()
    return PreferenceData(state.value) {
        scope.launch(Dispatchers.IO) {
            update(it)
        }
    }
}

/**
 simple extension function to plug in a setting as a dependency
 */
@Composable
fun <T> StorageSetting<T>.asDependency(enabled: (T) -> Boolean): Dependency.State<T> {
    val state = collectSetting()
    return Dependency.State(state, enabled)
}

/*
@Composable
fun <T> StorageSetting<T>.asMutableState(
    initialValue: T = getCached() ?: value
): MutableState<T> {
    val state = remember { mutableStateOf(initialValue) }
    val flowState = flow.collectAsState(initialValue)
    // delegate changes from the flow to the state
    LaunchedEffect(flowState.value) {
        state.value = flowState.value
    }
    // delegate changes from the state to the suspend update function
    LaunchedEffect(state.value) {
        withContext(Dispatchers.IO) {
            update(state.value)
        }
    }
    return state
}*/
package com.michaelflisar.composepreferences.kotpreferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * TODO:
 *  this uses caching (disabled by default) and a blocking read => not optimal
 * this is needed so that a preference screen does initially already know its visible and enabled states =>
 * otherwise each setting would evaluate this data step by step which does not lead to a visually appealing result...
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
 * simple extension function to plug in a setting as preference data provider and updater
 *
 * @param onValueChange the value changed callback of this item (return true to accept the change, false to cancel it)
 */
@Composable
fun <T> StorageSetting<T>.asPreferenceData(
    onValueChange: (data: T) -> Boolean = { true },
    onAfterValueChanged: ((data: T) -> Unit)? = null
): PreferenceData<T> {
    val scope = rememberCoroutineScope()
    val state = collectSetting()
    return PreferenceData(
        state.value
    ) { value ->
        if (onValueChange(value)) {
            scope.launch(Dispatchers.IO) {
                update(value)
                onAfterValueChanged?.let {
                    withContext(Dispatchers.Main) {
                        it(value)
                    }
                }
            }
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
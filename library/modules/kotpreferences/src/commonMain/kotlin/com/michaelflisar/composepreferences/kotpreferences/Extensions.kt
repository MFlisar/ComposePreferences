package com.michaelflisar.composepreferences.kotpreferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.kotpreferences.core.getValueNotNull
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import com.michaelflisar.kotpreferences.core.value

/* --8<-- [start: asDependency] */
/**
 * simple extension function to plug in a setting as a dependency
 *
 * @param enabled convert the value to a boolean to determine if the setting is enabled
 */
@Composable
fun <T> StorageSetting<T>.asDependency(
    enabled: (T) -> Boolean
): Dependency.State<T>
/* --8<-- [end: asDependency] */
{
    val state = collectSetting()
    return Dependency.State(state, enabled)
}

/* --8<-- [start: collect] */
/**
 * TODO:
 * this uses caching (disabled by default) and a blocking read => not optimal
 * this is needed so that a preference screen does initially already know its visible and enabled states =>
 * otherwise each setting would evaluate this data step by step which does not lead to a visually appealing result...
 *
 * Maybe I find a better solution?
 */
@Composable
private fun <T> StorageSetting<T>.collectSetting(
    initialValue: T = getValueNotNull()
): State<T>
/* --8<-- [end: collect] */
{
    return flow.collectAsState(initial = initialValue)
}
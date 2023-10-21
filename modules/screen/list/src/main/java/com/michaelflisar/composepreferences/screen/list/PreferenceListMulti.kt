package com.michaelflisar.composepreferences.screen.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.list.DialogList
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

@Composable
fun <T> PreferenceScope.PreferenceListMulti(
    // listStyle
    data: PreferenceData<List<T>>,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String,
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    formatter: @Composable (selected: List<T>) -> String = { selected ->
        selected.map {
            itemTextProvider(it)
        }.joinToString(";")
    },
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    PreferenceListMulti(
        value = data.value,
        onValueChange = data.onValueChange,
        items = items,
        itemTextProvider = itemTextProvider,
        itemIconProvider = itemIconProvider,
        formatter = formatter,
        title = title,
        enabled = enabled,
        visible = visible,
        subtitle = subtitle,
        icon = icon,
        preferenceStyle = preferenceStyle
    )
}

@Composable
fun <T> PreferenceScope.PreferenceListMulti(
    // Special
    value: List<T>,
    onValueChange: (value: List<T>) -> Unit,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String,
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    formatter: @Composable (selected: List<T>) -> String = { selected ->
        selected.map {
            itemTextProvider(it)
        }.joinToString(";")
    },
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    val showDialog = rememberDialogState()
    if (showDialog.showing) {
        val selection = remember(value) {
            mutableStateOf(value.map { items.indexOf(it) })
        }
        DialogList(
            state = showDialog,
            items = items,
            itemIdProvider = { items.indexOf(it) },
            selectionMode = DialogList.SelectionMode.MultiSelect<T>(
                selection
            ),
            itemContents = DialogList.ItemDefaultContent(
                text = itemTextProvider,
                icon = itemIconProvider
            ),
            title = title,
            icon = icon
        ) {
            if (it.isPositiveButton) {
                onValueChange(selection.value.map { items[it] })
            }
        }
    }
    BasePreference(
        enabled = enabled,
        visible = visible,
        title = title,
        subtitle = subtitle,
        icon = icon,
        preferenceStyle = preferenceStyle,
        onClick = {
            showDialog.show()
        }
    ) {
        PreferenceContentText(formatter(value))
    }
}

package com.michaelflisar.composepreferences.screen.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
fun <T> PreferenceScope.PreferenceList(
    style: PreferenceList.Style = PreferenceList.Style.Dialog,
    // listStyle
    data: PreferenceData<T>,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String,
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    PreferenceList(
        style = style,
        value = data.value,
        onValueChange = data.onValueChange,
        items = items,
        itemTextProvider = itemTextProvider,
        itemIconProvider = itemIconProvider,
        title = title,
        enabled = enabled,
        visible = visible,
        subtitle = subtitle,
        icon = icon,
        preferenceStyle = preferenceStyle
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PreferenceScope.PreferenceList(
    style: PreferenceList.Style = PreferenceList.Style.Dialog,
    // Special
    value: T,
    onValueChange: (value: T) -> Unit,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String,
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    when (style) {
        PreferenceList.Style.Dialog -> {
            val showDialog = rememberDialogState()
            if (showDialog.showing) {
                DialogList(
                    state = showDialog,
                    items = items,
                    itemIdProvider = { items.indexOf(it) },
                    selectionMode = DialogList.SelectionMode.SingleClickAndClose {
                        onValueChange(it)
                    },
                    itemContents = DialogList.ItemDefaultContent(
                        text = itemTextProvider,
                        icon = itemIconProvider
                    ),
                    title = title,
                    icon = icon
                ) {
                    if (it.isPositiveButton) {
                        onValueChange(value)
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
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemIconProvider?.let {
                        it(value)
                    }
                    PreferenceContentText(itemTextProvider(value))
                }
            }
        }

        PreferenceList.Style.Spinner -> {
            var expanded by remember { mutableStateOf(false) }
            BasePreference(
                //setup = PreferenceItemSetup(ignoreMinMaxContentWidth = true),
                enabled = enabled,
                visible = visible,
                title = title,
                subtitle = subtitle,
                icon = icon,
                preferenceStyle = preferenceStyle,
                onClick = {
                    if (!expanded)
                        expanded = true
                }
            ) {
                Box {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            itemIconProvider?.let {
                                it(value)
                            }
                            PreferenceContentText(text = itemTextProvider(value))
                        }
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                    }
                    if (expanded) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {
                            items.forEach { item ->
                                DropdownMenuItem(
                                    onClick = {
                                        if (item != value) {
                                            onValueChange(item)
                                        }
                                        expanded = false
                                    },
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            itemIconProvider?.let {
                                                it(item)
                                            }
                                            Text(text = itemTextProvider(item))
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Stable
object PreferenceList {
    enum class Style {
        Dialog, Spinner
    }
}

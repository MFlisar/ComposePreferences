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
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.helper.SearchText
import com.michaelflisar.composepreferences.core.internal.rememberPreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope

/**
 * A list preference item - this item provides a list dialog or a dropdown to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param style the [PreferenceList.Style] of this item ([PreferenceList.Style.Dialog] or [PreferenceList.Style.Spinner])
 * @param data the [PreferenceData] of this item
 * @param items the list of items that this preference can select from
 * @param itemTextProvider a converter to get the text of an item
 * @param itemIconProvider a converter to provide an icon for an item
 */
@Composable
fun <T> PreferenceScope.PreferenceList(
    style: PreferenceList.Style = PreferenceList.Style.Dialog,
    // listStyle
    data: PreferenceData<T>,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String = { it.toString() },
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceListDefaults.itemSetup(),
    filterTags: List<String> = emptyList()
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
        itemStyle = itemStyle,
        itemSetup = itemSetup,
        filterTags = filterTags
    )
}

/**
 * A list preference item - this item provides a list dialog or a dropdown to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param style the [PreferenceList.Style] of this item ([PreferenceList.Style.Dialog] or [PreferenceList.Style.Spinner])
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param items the list of items that this preference can select from
 * @param itemTextProvider a converter to get the text of an item
 * @param itemIconProvider a converter to provide an icon for an item
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PreferenceScope.PreferenceList(
    style: PreferenceList.Style = PreferenceList.Style.Dialog,
    // Special
    value: T,
    onValueChange: (value: T) -> Unit,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String = { it.toString() },
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceListDefaults.itemSetup(),
    filterTags: List<String> = emptyList()
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
                    title = { Text(title) },
                    icon = icon
                ) {
                    if (it.isPositiveButton) {
                        onValueChange(value)
                    }
                }
            }

            BasePreference(
                itemSetup = itemSetup,
                enabled = enabled,
                visible = visible,
                title = title,
                subtitle = subtitle,
                icon = icon,
                itemStyle = itemStyle,
                filterTags = filterTags,
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
                    PreferenceContentText(itemTextProvider(value), itemSetup)
                }
            }
        }

        PreferenceList.Style.Spinner -> {
            var expanded by remember { mutableStateOf(false) }

            BasePreference(
                itemSetup = itemSetup,
                enabled = enabled,
                visible = visible,
                title = title,
                subtitle = subtitle,
                icon = icon,
                itemStyle = itemStyle,
                filterTags = filterTags,
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
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                        ) {
                            itemIconProvider?.let {
                                it(value)
                            }
                            PreferenceContentText(text = itemTextProvider(value), itemSetup)
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

@Stable
object PreferenceListDefaults {
    @Composable
    fun itemSetup() = PreferenceItemSetup()
}
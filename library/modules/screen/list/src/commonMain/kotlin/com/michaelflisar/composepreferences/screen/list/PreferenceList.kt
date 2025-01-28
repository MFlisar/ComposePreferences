package com.michaelflisar.composepreferences.screen.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.michaelflisar.composedialogs.core.DialogState
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.list.DialogList
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.BasePreferenceDialog
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle

/* --8<-- [start: constructor] */
/**
 * A list preference item - this item provides a list dialog or a dropdown to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param style the [PreferenceList.Style] of this item ([PreferenceList.Style.Dialog], [PreferenceList.Style.Spinner] or [PreferenceList.Style.SegmentedButtons])
 * @param value the [MutableState] of this item
 * @param items the list of items that this preference can select from
 * @param itemTextProvider a converter to get the text of an item
 * @param itemIconProvider a converter to provide an icon for an item
 * @param filter a lambda for filtering the list - if it is null, filtering is disabled (works with [PreferenceList.Style.Dialog] only!)
 */
@Composable
fun <T> PreferenceScope.PreferenceList(
    style: PreferenceList.Style = PreferenceList.Style.Dialog(),
    // listStyle
    value: MutableState<T>,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String = { it.toString() },
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    filter: ((filter: String, item: T) -> Boolean)? = null,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceListDefaults.itemSetup(style),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceListDefaults.dialog(
            style,
            dialogState,
            value.value,
            { value.value = it },
            items,
            itemTextProvider,
            itemIconProvider,
            title,
            icon,
            filter?.let {
                DialogList.Filter(
                    filter = it,
                    keepSelectionForInvisibleItems = false
                )
            }
        )
    }
)
/* --8<-- [end: constructor] */
{
    PreferenceList(
        style = style,
        value = value.value,
        onValueChange = { value.value = it },
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
        titleRenderer = titleRenderer,
        subtitleRenderer = subtitleRenderer,
        filterTags = filterTags,
        dialog = dialog
    )
}

/* --8<-- [start: constructor2] */
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
@Composable
fun <T> PreferenceScope.PreferenceList(
    style: PreferenceList.Style = PreferenceList.Style.Dialog(),
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
    itemSetup: PreferenceItemSetup = PreferenceListDefaults.itemSetup(style),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceListDefaults.dialog(
            style,
            dialogState,
            value,
            onValueChange,
            items,
            itemTextProvider,
            itemIconProvider,
            title,
            icon
        )
    }
)
/* --8<-- [end: constructor2] */
{
    when (style) {
        is PreferenceList.Style.Dialog -> {
            BasePreferenceDialog(
                dialogState = rememberDialogState(),
                dialog = dialog,
                itemSetup = itemSetup,
                enabled = enabled,
                visible = visible,
                title = title,
                subtitle = subtitle,
                icon = icon,
                itemStyle = itemStyle,
                titleRenderer = titleRenderer,
                subtitleRenderer = subtitleRenderer,
                filterTags = filterTags
            ) {
                ContentDialogMode(value, itemSetup, itemIconProvider, itemTextProvider)
            }
        }

        PreferenceList.Style.Spinner -> {
            val expanded = remember { mutableStateOf(false) }
            BasePreference(
                itemSetup = itemSetup,
                enabled = enabled,
                visible = visible,
                title = title,
                subtitle = subtitle,
                icon = icon,
                itemStyle = itemStyle,
                titleRenderer = titleRenderer,
                subtitleRenderer = subtitleRenderer,
                filterTags = filterTags,
                onClick = {
                    if (!expanded.value)
                        expanded.value = true
                }
            ) {
                ContentDialogSpinner(
                    value,
                    onValueChange,
                    items,
                    itemSetup,
                    itemIconProvider,
                    itemTextProvider,
                    expanded
                )
            }
        }

        PreferenceList.Style.SegmentedButtons -> {
            BasePreference(
                itemSetup = itemSetup,
                enabled = enabled,
                visible = visible,
                title = title,
                subtitle = subtitle,
                icon = icon,
                itemStyle = itemStyle,
                titleRenderer = titleRenderer,
                subtitleRenderer = subtitleRenderer,
                filterTags = filterTags,
                onClick = null
            ) {
                ContentDialogSegmentedButtons(
                    value,
                    onValueChange,
                    items,
                    itemIconProvider,
                    itemTextProvider
                )
            }
        }
    }
}

@Composable
private fun <T> ColumnScope.ContentDialogMode(
    value: T,
    itemSetup: PreferenceItemSetup,
    itemIconProvider: @Composable ((T) -> Unit)?,
    itemTextProvider: @Composable (T) -> String
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> ContentDialogSpinner(
    value: T,
    onValueChange: (value: T) -> Unit,
    items: List<T>,
    itemSetup: PreferenceItemSetup,
    itemIconProvider: @Composable ((item: T) -> Unit)?,
    itemTextProvider: @Composable (item: T) -> String,
    expanded: MutableState<Boolean>
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
            ExposedDropdownMenuDefaults.TrailingIcon(expanded.value)
        }
        if (expanded.value) {
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            if (item != value) {
                                onValueChange(item)
                            }
                            expanded.value = false
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

@Composable
private fun <T> ColumnScope.ContentDialogSegmentedButtons(
    value: T,
    onValueChange: (value: T) -> Unit,
    items: List<T>,
    itemIconProvider: @Composable ((item: T) -> Unit)?,
    itemTextProvider: @Composable (item: T) -> String
) {
    Row(
        modifier = Modifier.align(Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                SegmentedButton(
                    selected = item == value,
                    shape = SegmentedButtonDefaults.itemShape(index, items.size),
                    onClick = { onValueChange(item) },
                    icon = if (item == value) {
                        { SegmentedButtonDefaults.Icon(true) }
                    } else {
                        { itemIconProvider?.invoke(item) }
                    },
                    label = { Text(itemTextProvider(item)) }
                )
            }
        }
    }

}

@Stable
object PreferenceList {
    sealed class Style {
        class Dialog(
            val closeOnItemClick: Boolean = false
        ) : Style()
        data object Spinner : Style()
        data object SegmentedButtons : Style()
    }
}

@Stable
object PreferenceListDefaults {

    @Composable
    fun itemSetup(style: PreferenceList.Style) : PreferenceItemSetup {
        return when (style) {
            is PreferenceList.Style.Dialog,
            PreferenceList.Style.Spinner -> PreferenceItemSetup()
            PreferenceList.Style.SegmentedButtons -> PreferenceItemSetup(
                ignoreForceNoIconInset = true,
                hideTitle = true,
                contentPlacementBottom = true
            )
        }
    }

    @Composable
    fun <T> dialog(
        style: PreferenceList.Style,
        dialogState: DialogState,
        value: T,
        onValueChange: (value: T) -> Unit,
        items: List<T>,
        itemTextProvider: @Composable (item: T) -> String = { it.toString() },
        itemIconProvider: (@Composable (item: T) -> Unit)? = null,
        title: String,
        icon: (@Composable () -> Unit)? = null,
        filter: DialogList.Filter<T>? = null
    ) {
        val selected = remember { mutableStateOf(items.indexOf(value).takeIf { it >= 0 }) }
        DialogList(
            state = dialogState,
            items = items,
            itemIdProvider = { items.indexOf(it) },
            selectionMode = DialogList.SelectionMode.SingleSelect(
                selected = selected,
                selectOnRadioButtonClickOnly = false,
                closeOnSelect = style is PreferenceList.Style.Dialog && style.closeOnItemClick
            ),
            itemContents = DialogList.ItemDefaultContent(
                text = itemTextProvider,
                icon = itemIconProvider
            ),
            title = { Text(title) },
            icon = icon,
            filter = filter
        ) {
            if (it.isPositiveButton) {
                val newValue = selected.value?.let { items[it] } ?: value
                onValueChange(newValue)
            }
        }
    }
}
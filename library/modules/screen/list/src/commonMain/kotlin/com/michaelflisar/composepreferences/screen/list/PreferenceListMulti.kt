package com.michaelflisar.composepreferences.screen.list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
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
 * A list preference item - this item provides a list dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the [MutableState] of this item
 * @param items the list of items that this preference can select from
 * @param itemTextProvider a converter to get the text of an item
 * @param itemIconProvider a converter to provide an icon for an item
 */
@Composable
fun <T> PreferenceScope.PreferenceListMulti(
    // listStyle
    value: MutableState<List<T>>,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String = { it.toString() },
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    formatter: @Composable (selected: List<T>) -> String = { selected ->
        selected.map {
            itemTextProvider(it)
        }.joinToString(";")
    },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceMultiListDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceMultiListDefaults.dialog(dialogState, value.value, { value.value = it }, items, itemTextProvider, itemIconProvider, title, icon)
    }
)
/* --8<-- [end: constructor] */
{
    PreferenceListMulti(
        value = value.value,
        onValueChange = { value.value = it },
        items = items,
        itemTextProvider = itemTextProvider,
        itemIconProvider = itemIconProvider,
        formatter = formatter,
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
 * A list preference item - this item provides a list dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param items the list of items that this preference can select from
 * @param itemTextProvider a converter to get the text of an item
 * @param itemIconProvider a converter to provide an icon for an item
 */
@Composable
fun <T> PreferenceScope.PreferenceListMulti(
    // Special
    value: List<T>,
    onValueChange: (value: List<T>) -> Unit,
    items: List<T>,
    itemTextProvider: @Composable (item: T) -> String = { it.toString() },
    itemIconProvider: (@Composable (item: T) -> Unit)? = null,
    formatter: @Composable (selected: List<T>) -> String = { selected ->
        selected.map {
            itemTextProvider(it)
        }.joinToString(";")
    },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceMultiListDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceMultiListDefaults.dialog(dialogState, value, onValueChange, items, itemTextProvider, itemIconProvider, title, icon)
    }
)
/* --8<-- [end: constructor2] */
{
    BasePreferenceDialog(
        dialogState = rememberDialogState(),
        dialog = dialog,
        enabled = enabled,
        visible = visible,
        title = title,
        subtitle = subtitle,
        icon = icon,
        itemStyle = itemStyle,
        titleRenderer = titleRenderer,
        subtitleRenderer = subtitleRenderer,
        filterTags = filterTags,
        itemSetup = itemSetup
    ) {
        PreferenceContentText(formatter(value), itemSetup)
    }
}

@Stable
object PreferenceMultiListDefaults {

    @Composable
    fun itemSetup() = PreferenceItemSetup()

    @Composable
    fun <T> dialog(
        dialogState: DialogState,
        value: List<T>,
        onValueChange: (value: List<T>) -> Unit,
        items: List<T>,
        itemTextProvider: @Composable (item: T) -> String = { it.toString() },
        itemIconProvider: (@Composable (item: T) -> Unit)? = null,
        title: String,
        icon: (@Composable () -> Unit)? = null
    ) {
        val selection = remember(value) {
            mutableStateOf(value.map { items.indexOf(it) })
        }
        DialogList(
            state = dialogState,
            items = items,
            itemIdProvider = { items.indexOf(it) },
            selectionMode = DialogList.SelectionMode.MultiSelect<T>(
                selection
            ),
            itemContents = DialogList.ItemDefaultContent(
                text = itemTextProvider,
                icon = itemIconProvider
            ),
            title = { Text(title) },
            icon = icon
        ) {
            if (it.isPositiveButton) {
                onValueChange(selection.value.map { items[it] })
            }
        }
    }
}
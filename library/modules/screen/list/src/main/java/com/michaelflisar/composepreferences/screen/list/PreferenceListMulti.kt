package com.michaelflisar.composepreferences.screen.list

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.list.DialogList
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreviewPreference
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

/**
 * A list preference item - this item provides a list dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param data the [PreferenceData] of this item
 * @param items the list of items that this preference can select from
 * @param itemTextProvider a converter to get the text of an item
 * @param itemIconProvider a converter to provide an icon for an item
 */
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
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceMultiListDefaults.itemSetup()
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
        preferenceStyle = preferenceStyle,
        itemSetup = itemSetup
    )
}

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
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceMultiListDefaults.itemSetup()
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
        itemSetup = itemSetup,
        onClick = {
            showDialog.show()
        }
    ) {
        PreferenceContentText(formatter(value), itemSetup)
    }
}

@Stable
object PreferenceMultiListDefaults {
    @Composable
    fun itemSetup() = PreferenceItemSetup()
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceListMulti(
            value = (0..10).map { "Value $it" },
            onValueChange = {},
            items = (0..10).map { "Value $it" },
            itemTextProvider = { it },
            icon = { Icon(Icons.Default.List, null) },
            title = { Text(text = "List Preference") },
            subtitle = { Text(text = "Clicking this item will open a dialog to select one, more or no list entry") },
        )
    }
}
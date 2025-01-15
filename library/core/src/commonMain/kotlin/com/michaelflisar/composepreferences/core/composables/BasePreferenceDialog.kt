package com.michaelflisar.composepreferences.core.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import com.michaelflisar.composedialogs.core.DialogStateNoData
import com.michaelflisar.composepreferences.core.PreferenceInfoDefaults
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle

/**
 * A dialog preference item
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param dialogState the state of the dialog
 * @param dialog the dialog composable
 */
@Composable
fun PreferenceScope.BasePreferenceDialog(
    // Special
    dialogState: DialogStateNoData,
    dialog: @Composable (state: DialogStateNoData) -> Unit,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInfoDefaults.itemSetup(),
    filterTags: List<String> = emptyList(),
    content: (@Composable ColumnScope.() -> Unit)? = null
) {
    // Dialog
    if (dialogState.visible) {
        dialog(dialogState)
    }

    // Preference
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
            dialogState.show()
        },
        content = content
    )
}
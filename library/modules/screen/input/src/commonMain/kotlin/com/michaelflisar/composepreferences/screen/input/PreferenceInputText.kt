package com.michaelflisar.composepreferences.screen.input

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.input.DialogInput
import com.michaelflisar.composedialogs.dialogs.input.DialogInputNumber
import com.michaelflisar.composedialogs.dialogs.input.DialogInputValidator
import com.michaelflisar.composedialogs.dialogs.input.rememberDialogInput
import com.michaelflisar.composedialogs.dialogs.input.rememberDialogInputValidator
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceType
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.helper.SearchText
import com.michaelflisar.composepreferences.core.internal.rememberPreferenceItemState
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope

/**
 * A text preference item - this item provides a text input dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param data the [PreferenceData] of this item
 * @param validator the [DialogInputValidator] of this item
 */
@Composable
fun PreferenceScope.PreferenceInputText(
    // Special
    data: PreferenceData<String>,
    validator: DialogInputValidator = rememberDialogInputValidator(),
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceBoolDefaults.itemSetup(),
    filterTags: List<String> = emptyList()
) {
    PreferenceInputText(
        value = data.value,
        onValueChange = data.onValueChange,
        validator = validator,
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
 * A text preference item - this item provides a text input dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param validator the [DialogInputValidator] of this item
 */
@Composable
fun PreferenceScope.PreferenceInputText(
    // Special
    value: String,
    onValueChange: (value: String) -> Unit,
    validator: DialogInputValidator = rememberDialogInputValidator(),
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceBoolDefaults.itemSetup(),
    filterTags: List<String> = emptyList()
) {
    val showDialog = rememberDialogState()
    if (showDialog.showing) {
        val value = rememberDialogInput(value)
        DialogInput(
            state = showDialog,
            input = value,
            title = { Text(title) },
            icon = icon,
            validator = validator
        ) {
            if (it.isPositiveButton) {
                onValueChange(value.value)
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
        PreferenceContentText(value, itemSetup)
    }
}

@Stable
object PreferenceBoolDefaults {
    @Composable
    fun itemSetup() = PreferenceItemSetup()
}


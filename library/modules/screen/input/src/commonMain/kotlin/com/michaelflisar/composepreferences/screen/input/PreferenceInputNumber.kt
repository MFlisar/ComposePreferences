package com.michaelflisar.composepreferences.screen.input

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import com.michaelflisar.composedialogs.core.DialogState
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.input.DialogInputNumber
import com.michaelflisar.composedialogs.dialogs.input.DialogInputValidator
import com.michaelflisar.composedialogs.dialogs.input.rememberDialogInputNumber
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle
import com.michaelflisar.composepreferences.core.composables.BasePreferenceDialog
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope

/**
 * A number input preference item - this item provides a input dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the [MutableState] of this item
 * @param validator the [DialogInputValidator] of this item
 * @param formatter the formatter of this item
 */
@Composable
fun <T : Number> PreferenceScope.PreferenceInputNumber(
    // Special
    value: MutableState<T>,
    validator: DialogInputValidator = DialogInputNumber.rememberDefaultValidator(value.value),
    formatter: (value: T) -> String = { it.toString() },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInputNumberDefaults.itemSetup(),
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceInputNumberDefaults.dialog(dialogState, value.value, { value.value = it }, validator, title, icon)
    }
) {
    PreferenceInputNumber(
        value = value.value,
        onValueChange = { value.value = it },
        validator = validator,
        formatter = formatter,
        title = title,
        enabled = enabled,
        visible = visible,
        subtitle = subtitle,
        icon = icon,
        itemStyle = itemStyle,
        itemSetup = itemSetup,
        filterTags = filterTags,
        dialog = dialog
    )
}

/**
 * A number input preference item - this item provides a input dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 * @param validator the [DialogInputValidator] of this item
 * @param formatter the formatter of this item
 */
@Composable
fun <T : Number> PreferenceScope.PreferenceInputNumber(
    // Special
    value: T,
    onValueChange: (value: T) -> Unit,
    validator: DialogInputValidator = DialogInputNumber.rememberDefaultValidator(value),
    formatter: (value: T) -> String = { it.toString() },
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInputNumberDefaults.itemSetup(),
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceInputNumberDefaults.dialog(dialogState, value, onValueChange, validator, title, icon)
    }
) {
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
        filterTags = filterTags
    ) {
        PreferenceContentText(formatter(value), itemSetup)
    }
}

@Stable
object PreferenceInputNumberDefaults {

    @Composable
    fun itemSetup() = PreferenceItemSetup(
        trailingContentSize = PreferenceItemSetupDefaults.numericContent()
    )

    @Composable
    fun <T : Number> dialog(
        dialogState: DialogState,
        value: T,
        onValueChange: (value: T) -> Unit,
        validator: DialogInputValidator = DialogInputNumber.rememberDefaultValidator(value),
        title: String,
        icon: (@Composable () -> Unit)? = null
    ) {
        val value = rememberDialogInputNumber(value)
        DialogInputNumber(
            state = dialogState,
            value = value,
            title = { Text(title) },
            icon = icon,
            validator = validator
        ) {
            if (it.isPositiveButton) {
                onValueChange(value.value)
            }
        }
    }
}
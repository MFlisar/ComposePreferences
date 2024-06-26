package com.michaelflisar.composepreferences.screen.input

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.input.DialogInputNumber
import com.michaelflisar.composedialogs.dialogs.input.DialogInputValidator
import com.michaelflisar.composedialogs.dialogs.input.rememberDialogInputNumber
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.composables.PreviewPreference
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

/**
 * A color preference item - this item provides a input dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param data the [PreferenceData] of this item
 * @param validator the [DialogInputValidator] of this item
 * @param formatter the formatter of this item
 */
@Composable
fun <T : Number> PreferenceScope.PreferenceInputNumber(
    // Special
    data: PreferenceData<T>,
    validator: DialogInputValidator = DialogInputNumber.rememberDefaultValidator(data.value),
    formatter: (value: T) -> String = { it.toString() },
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInputNumberDefaults.itemSetup()
) {
    PreferenceInputNumber(
        value = data.value,
        onValueChange = data.onValueChange,
        validator = validator,
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
 * A color preference item - this item provides a input dialog to change this preference
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
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceInputNumberDefaults.itemSetup()
) {
    val showDialog = rememberDialogState()
    if (showDialog.showing) {
        val value = rememberDialogInputNumber(value)
        DialogInputNumber(
            state = showDialog,
            value = value,
            title = title,
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
        preferenceStyle = preferenceStyle,
        onClick = {
            showDialog.show()
        }
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
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceInputNumber(
            value = 100,
            onValueChange = {},
            icon = { Icon(Icons.Default.Add, null) },
            title = { Text(text = "Input Number") },
            subtitle = { Text(text = "Input any valid INTEGER inside a dialog with an input field") },
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview2() {
    PreviewPreference {
        PreferenceInputNumber(
            value = 3.5f,
            onValueChange = {},
            icon = { Icon(Icons.Default.Add, null) },
            title = { Text(text = "Input Number") },
            subtitle = { Text(text = "Input any valid FLOAT inside a dialog with an input field") },
        )
    }
}
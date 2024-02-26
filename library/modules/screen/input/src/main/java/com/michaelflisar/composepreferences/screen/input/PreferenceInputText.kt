package com.michaelflisar.composepreferences.screen.input

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.input.DialogInput
import com.michaelflisar.composedialogs.dialogs.input.rememberDialogInput
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.PreferenceContentText
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.composables.PreviewPreference
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

/**
 * A text preference item - this item provides a text input dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param data the [PreferenceData] of this item
 */
@Composable
fun PreferenceScope.PreferenceInputText(
    // Special
    data: PreferenceData<String>,
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceBoolDefaults.itemSetup()
) {
    PreferenceInputText(
        value = data.value,
        onValueChange = data.onValueChange,
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
 * A text preference item - this item provides a text input dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the value of this item
 * @param onValueChange the value changed callback of this item
 */
@Composable
fun PreferenceScope.PreferenceInputText(
    // Special
    value: String,
    onValueChange: (value: String) -> Unit,
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle,
    itemSetup: PreferenceItemSetup = PreferenceBoolDefaults.itemSetup()
) {
    val showDialog = rememberDialogState()
    if (showDialog.showing) {
        val value = rememberDialogInput(value)
        DialogInput(
            state = showDialog,
            input = value,
            title = title,
            icon = icon
        ) {
            if (it.isPositiveButton) {
                onValueChange(value.value)
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
        PreferenceContentText(value)
    }
}

@Stable
object PreferenceBoolDefaults {
    @Composable
    fun itemSetup() = PreferenceItemSetup()
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceInputText(
            value = "Hello",
            onValueChange = {},
            icon = { Icon(Icons.Default.MailOutline, null) },
            title = { Text(text = "Input Text") },
            subtitle = { Text(text = "Input some text inside a dialog") },
        )
    }
}
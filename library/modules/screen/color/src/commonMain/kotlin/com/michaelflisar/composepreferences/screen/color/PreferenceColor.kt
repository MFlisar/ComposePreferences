package com.michaelflisar.composepreferences.screen.color

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.michaelflisar.composedialogs.core.DialogEventType
import com.michaelflisar.composedialogs.core.DialogState
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.color.DialogColor
import com.michaelflisar.composedialogs.dialogs.color.DialogColorUtil
import com.michaelflisar.composedialogs.dialogs.color.rememberDialogColor
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.composables.BasePreferenceDialog
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.PreferenceItemStyle

// begin-snippet: PreferenceColor::constructor
/**
 * A color preference item - this item provides a color dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the [MutableState] of this item
 * @param alphaSupported if true, this preference does support alpha values (ARGB) otherwise it doesn't (RGB only)
 * @param directEditSupported if true, this preference does allow direct editing of rgba values (in the custom color page)
 */
@Composable
fun PreferenceScope.PreferenceColor(
    // Special
    value: MutableState<Color>,
    alphaSupported: Boolean = true,
    directEditSupported: Boolean = false,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceColorDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceColorDefaults.dialog(dialogState, value.value, { value.value = it }, alphaSupported, directEditSupported, title, icon)
    }
)
// end-snippet
{
    PreferenceColor(
        value = value.value,
        onValueChange = { value.value = it },
        alphaSupported = alphaSupported,
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

// begin-snippet: PreferenceColor::constructor2
/**
 * A color preference item - this item provides a color dialog to change this preference
 *
 * &nbsp;
 *
 * **Basic Parameters:** all params not described here are derived from [com.michaelflisar.composepreferences.core.composables.BasePreference], check it out for more details
 *
 * @param value the color value of this item
 * @param onValueChange the value changed callback of this item
 * @param alphaSupported if true, this preference does support alpha values (ARGB) otherwise it doesn't (RGB only)
 */
@Composable
fun PreferenceScope.PreferenceColor(
    // Special
    value: Color,
    onValueChange: (value: Color) -> Unit,
    alphaSupported: Boolean = true,
    // Base Preference
    title: String,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    itemStyle: PreferenceItemStyle = LocalPreferenceSettings.current.style.defaultItemStyle,
    itemSetup: PreferenceItemSetup = PreferenceColorDefaults.itemSetup(),
    titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
    filterTags: List<String> = emptyList(),
    // Dialog
    dialog: @Composable (state: DialogState) -> Unit = { dialogState ->
        PreferenceColorDefaults.dialog(dialogState, value, onValueChange, alphaSupported, title, icon)
    }
)
// end-snippet
{
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
        Content(value)
    }
}

@Composable
private fun Content(value: Color) {
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    DialogColorUtil.drawCheckerboard(this, density)
                }
                .background(value)
        )
    }
}

@Stable
object PreferenceColorDefaults {

    @Composable
    fun itemSetup() = PreferenceItemSetup(
        trailingContentSize = PreferenceItemSetupDefaults.trailingContentSize(0.dp)
    )

    @Composable
    fun dialog(
        dialogState: DialogState,
        value: Color,
        onValueChange: (value: Color) -> Unit,
        alphaSupported: Boolean,
        directEditSupported: Boolean,
        title: String,
        icon: (@Composable () -> Unit)? = null
    ) {
        val value = rememberDialogColor(value)
        DialogColor(
            state = dialogState,
            color = value,
            alphaSupported = alphaSupported,
            directEditSupported = directEditSupported,
            title = { Text(title) },
            icon = icon
        ) {
            if (it.type == DialogEventType.ButtonPositive) {
                onValueChange(value.value)
            }
        }
    }
}
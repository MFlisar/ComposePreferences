package com.michaelflisar.composepreferences.screen.color

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelflisar.composedialogs.core.rememberDialogState
import com.michaelflisar.composedialogs.dialogs.color.DialogColor
import com.michaelflisar.composedialogs.dialogs.color.DialogColorUtil
import com.michaelflisar.composedialogs.dialogs.color.rememberDialogColor
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.classes.LocalPreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceData
import com.michaelflisar.composepreferences.core.classes.PreferenceStyle
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetupDefaults
import com.michaelflisar.composepreferences.core.composables.PreviewPreference
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope

@Composable
fun PreferenceScope.PreferenceColor(
    // Special
    data: PreferenceData<Color>,
    alphaSupported: Boolean = true,
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    PreferenceColor(
        value = data.value,
        onValueChange = data.onValueChange,
        alphaSupported = alphaSupported,
        title = title,
        enabled = enabled,
        visible = visible,
        subtitle = subtitle,
        icon = icon,
        preferenceStyle = preferenceStyle
    )
}

@Composable
fun PreferenceScope.PreferenceColor(
    // Special
    value: Color,
    onValueChange: (value: Color) -> Unit,
    alphaSupported: Boolean = true,
    // Base Preference
    title: @Composable () -> Unit,
    enabled: Dependency = Dependency.Enabled,
    visible: Dependency = Dependency.Enabled,
    subtitle: @Composable (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    preferenceStyle: PreferenceStyle = LocalPreferenceSettings.current.itemStyle
) {
    val density = LocalDensity.current
    val showDialog = rememberDialogState()
    if (showDialog.showing) {
        val value = rememberDialogColor(value)
        DialogColor(
            state = showDialog,
            color = value,
            alphaSupported = alphaSupported,
            title = title,
            icon = icon
        ) {
            if (it.isPositiveButton) {
                onValueChange(value.value)
            }
        }
    }
    BasePreference(
        setup = PreferenceItemSetup(
            trailingContentSize = PreferenceItemSetupDefaults.trailingContentSize(0.dp)
        ),
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
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview() {
    PreviewPreference {
        PreferenceColor(
            value = Color.Blue,
            onValueChange = {},
            icon = { Icon(Icons.Default.Favorite, null) },
            title = { Text(text = "Color Preference") },
            subtitle = { Text(text = "This preference does allow to select any RGB color") }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun Preview2() {
    PreviewPreference {
        PreferenceColor(
            value = Color.Red.copy(alpha = .5f),
            onValueChange = {},
            icon = { Icon(Icons.Default.Favorite, null) },
            title = { Text(text = "Color Preference") },
            subtitle = { Text(text = "This preference does allow to select any ARGB color") }
        )
    }
}
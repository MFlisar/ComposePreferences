package com.michaelflisar.composepreferences.screen.bool

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.preview.PreviewPreference

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceBool() {
    PreviewPreference {
        PreferenceBool(
            style = PreferenceBool.Style.Checkbox,
            value = true,
            onValueChange = {},
            icon = { Icon(Icons.Default.Info, null) },
            title = "Checkbox Title",
            subtitle = "This is a description"
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceBool2() {
    PreviewPreference {
        PreferenceBool(
            style = PreferenceBool.Style.Switch,
            value = true,
            onValueChange = {},
            icon = { Icon(Icons.Default.Info, null) },
            title = "Switch Title",
            subtitle = "This is a description"
        )
    }
}
package com.michaelflisar.composepreferences.screen.input

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.preview.PreviewPreference

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceInputNumber() {
    PreviewPreference {
        PreferenceInputNumber(
            value = 100,
            onValueChange = {},
            icon = { Icon(Icons.Default.Add, null) },
            title = "Input Number",
            subtitle = "Input any valid INTEGER inside a dialog with an input field"
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceInputNumber2() {
    PreviewPreference {
        PreferenceInputNumber(
            value = 3.5f,
            onValueChange = {},
            icon = { Icon(Icons.Default.Add, null) },
            title = "Input Number",
            subtitle = "Input any valid FLOAT inside a dialog with an input field"
        )
    }
}
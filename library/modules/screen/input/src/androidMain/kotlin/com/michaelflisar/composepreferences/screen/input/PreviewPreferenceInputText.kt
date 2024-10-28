package com.michaelflisar.composepreferences.screen.input

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.preview.PreviewPreference

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceInputText() {
    PreviewPreference {
        PreferenceInputText(
            value = "Hello",
            onValueChange = {},
            icon = { Icon(Icons.Default.MailOutline, null) },
            title = "Input Text",
            subtitle = "Input some text inside a dialog"
        )
    }
}
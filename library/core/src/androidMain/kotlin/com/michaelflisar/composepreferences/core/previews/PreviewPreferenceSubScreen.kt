package com.michaelflisar.composepreferences.core.previews

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.PreferenceSubScreen
import com.michaelflisar.composepreferences.core.preview.PreviewPreference

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceSubScreen() {
    PreviewPreference {
        PreferenceSubScreen(
            icon = { Icon(Icons.Default.Info, null) },
            title = "Sub Screen Preference",
            subtitle = "This is a description"
        ) {
        }
    }
}
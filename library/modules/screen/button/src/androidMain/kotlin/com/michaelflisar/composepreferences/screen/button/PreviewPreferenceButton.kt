package com.michaelflisar.composepreferences.screen.button

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.preview.PreviewPreference

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceButton() {
    PreviewPreference {
        PreferenceButton(
            onClick = {},
            icon = { Icon(Icons.Default.Share, null) },
            title = "Button Preference",
            subtitle = "Click this item to execute an action"
        )
    }
}
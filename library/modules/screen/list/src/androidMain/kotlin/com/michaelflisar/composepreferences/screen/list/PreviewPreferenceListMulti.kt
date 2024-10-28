package com.michaelflisar.composepreferences.screen.list

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.preview.PreviewPreference

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceListMulti() {
    PreviewPreference {
        PreferenceListMulti(
            value = (0..10).map { "Value $it" },
            onValueChange = {},
            items = (0..10).map { "Value $it" },
            itemTextProvider = { it },
            icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
            title = "List Preference",
            subtitle = "Clicking this item will open a dialog to select one, more or no list entry"
        )
    }
}
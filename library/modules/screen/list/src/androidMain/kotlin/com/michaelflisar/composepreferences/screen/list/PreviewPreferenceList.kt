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
private fun PreviewPreferenceList() {
    PreviewPreference {
        PreferenceList(
            value = "Value 1",
            onValueChange = {},
            items = listOf("Value 1", "Value 2"),
            itemTextProvider = { it },
            icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
            title = "List Preference",
            subtitle = "Clicking this item will open a dialog to select a list entry"
        )
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceList2() {
    PreviewPreference {
        PreferenceList(
            style = PreferenceList.Style.Spinner,
            value = "Value 1",
            onValueChange = {},
            items = listOf("Value 1", "Value 2"),
            itemTextProvider = { it },
            icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
            title = "List Preference",
            subtitle = "Clicking this item will open a dropdown to select a list entry"
        )
    }
}
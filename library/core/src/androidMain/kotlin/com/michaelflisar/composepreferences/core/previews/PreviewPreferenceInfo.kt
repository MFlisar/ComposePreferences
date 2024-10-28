package com.michaelflisar.composepreferences.core.previews

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.preview.PreviewPreference

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceInfo() {
    PreviewPreference {
        PreferenceInfo(
            icon = { Icon(Icons.Default.Info, null) },
            title = "Info Preference",
            subtitle ="This is a description"
        )
    }
}
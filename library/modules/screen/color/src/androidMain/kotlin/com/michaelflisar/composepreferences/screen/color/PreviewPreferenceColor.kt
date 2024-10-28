package com.michaelflisar.composepreferences.screen.color

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.preview.PreviewPreference

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceColor() {
    PreviewPreference {
        PreferenceColor(
            value = Color.Blue,
            onValueChange = {},
            icon = { Icon(Icons.Default.Favorite, null) },
            title = "Color Preference",
            subtitle = "This preference does allow to select any RGB color"
        )
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceColor2() {
    PreviewPreference {
        PreferenceColor(
            value = Color.Red.copy(alpha = .5f),
            onValueChange = {},
            icon = { Icon(Icons.Default.Favorite, null) },
            title = "Color Preference",
            subtitle = "This preference does allow to select any ARGB color"
        )
    }
}
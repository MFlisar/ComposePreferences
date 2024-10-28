package com.michaelflisar.composepreferences.screen.number

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.preview.PreviewPreference

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceNumber() {
    PreviewPreference {
        PreferenceNumber(
            value = 3,
            onValueChange = {},
            min = 0,
            max = 5,
            stepSize = 1,
            icon = { Icon(Icons.Default.Star, null) },
            title = "Input Number Title",
            subtitle = "This allows you to PICK any valid INTEGER inside a dialog with a number picker"
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceNumber2() {
    PreviewPreference {
        PreferenceNumber(
            style = PreferenceNumber.Style.Slider(),
            value = 3,
            onValueChange = {},
            min = 0,
            max = 5,
            stepSize = 1,
            icon = { Icon(Icons.Default.Star, null) },
            title = "Input Number Title",
            subtitle = "Select a number with a slider"
        )
    }
}
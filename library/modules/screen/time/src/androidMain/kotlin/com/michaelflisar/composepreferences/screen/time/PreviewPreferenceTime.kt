package com.michaelflisar.composepreferences.screen.time

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.preview.PreviewPreference
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceTime() {
    PreviewPreference {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val localTime = LocalTime(
            localDateTime.hour,
            localDateTime.minute,
            localDateTime.second,
            localDateTime.nanosecond
        )
        PreferenceTime(
            value = localTime,
            onValueChange = {},
            is24Hours = true,
            icon = { Icon(Icons.Default.Refresh, null) },
            title = "Time Title",
            subtitle = "Select a time inside a time picker dialog"
        )
    }
}
package com.michaelflisar.composepreferences.screen.date

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.michaelflisar.composepreferences.core.preview.PreviewPreference
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun PreviewPreferenceDate() {
    PreviewPreference {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val localDate = LocalDate(localDateTime.year, localDateTime.month, localDateTime.dayOfMonth)
        PreferenceDate(
            value = localDate,
            onValueChange = {},
            icon = { Icon(Icons.Default.DateRange, null) },
            title = "Date Preference",
            subtitle = "Click to open a date selector dialog"
        )
    }
}
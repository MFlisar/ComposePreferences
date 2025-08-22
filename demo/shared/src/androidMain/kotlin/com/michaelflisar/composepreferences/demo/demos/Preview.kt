package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.PreferenceSection
import com.michaelflisar.composepreferences.core.preview.PreviewPreference
import com.michaelflisar.composepreferences.core.scopes.PreferenceGroupScope
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.button.PreferenceButton
import com.michaelflisar.composepreferences.screen.color.PreferenceColor
import com.michaelflisar.composepreferences.screen.date.PreferenceDate
import com.michaelflisar.composepreferences.screen.list.PreferenceList
import com.michaelflisar.composepreferences.screen.list.PreferenceListMulti
import com.michaelflisar.composepreferences.screen.number.PreferenceNumber
import com.michaelflisar.composepreferences.screen.time.PreferenceTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Preview(
    widthDp = 1200
)
@Composable
private fun PreviewAll() {
    Column(
        //verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .border(1.dp, MaterialTheme.colorScheme.onBackground)
    ) {
        RowHeader("Info Preference", "Section Header Preference", weight2 = 2f)
        RowPreferences(
            {
                PreferenceInfo(
                    icon = { Icon(Icons.Default.Info, null) },
                    title = "Info Preference",
                    subtitle = "This is a description"
                )
            },
            {
                PreferenceSection(
                    title = "Section Header"
                ) {}
            },
            {
                PreferenceSection(
                    icon = { Icon(Icons.Default.Info, null) },
                    title = "Section Header",
                    subtitle = "This is a description"
                ) {}
            }
        )
        RowHeader("Checkbox Preference", "Button Preference", weight1 = 2f)
        RowPreferences(
            {
                PreferenceBool(
                    style = PreferenceBool.Style.Checkbox,
                    value = true,
                    onValueChange = {},
                    icon = { Icon(Icons.Default.Info, null) },
                    title = "Checkbox Title",
                    subtitle = "This is a description",
                )
            },
            {
                PreferenceBool(
                    style = PreferenceBool.Style.Switch,
                    value = true,
                    onValueChange = {},
                    icon = { Icon(Icons.Default.Info, null) },
                    title = "Switch Title",
                    subtitle = "This is a description",
                )
            },
            {
                PreferenceButton(
                    onClick = {},
                    icon = { Icon(Icons.Default.Share, null) },
                    title = "Button Preference",
                    subtitle = "Click this item to execute an action"
                )
            }
        )
        RowHeader("Color Preference")
        RowPreferences(
            {
                PreferenceColor(
                    value = Color.Blue,
                    onValueChange = {},
                    icon = { Icon(Icons.Default.Favorite, null) },
                    title = "Color Preference",
                    subtitle = "This preference does allow to select any RGB color"
                )
            },
            {
                PreferenceColor(
                    value = Color.Red.copy(alpha = .5f),
                    onValueChange = {},
                    icon = { Icon(Icons.Default.Favorite, null) },
                    title = "Color Preference",
                    subtitle = "This preference does allow to select any ARGB color"
                )
            },
            null
        )
        RowHeader("Date/Time Preference")

        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val localDate = LocalDate(
            localDateTime.year,
            localDateTime.month,
            localDateTime.day
        )
        val localTime = LocalTime(
            localDateTime.hour,
            localDateTime.minute,
            localDateTime.second,
            localDateTime.nanosecond
        )
        RowPreferences(
            {
                PreferenceDate(
                    value = localDate,
                    onValueChange = {},
                    icon = { Icon(Icons.Default.DateRange, null) },
                    title = "Date Preference",
                    subtitle = "Click to open a date selector dialog",
                )
            },
            {
                PreferenceTime(
                    value = localTime,
                    onValueChange = {},
                    is24Hours = true,
                    icon = { Icon(Icons.Default.Refresh, null) },
                    title = "Time Title",
                    subtitle = "Select a time inside a time picker dialog",
                )
            },
            null
        )
        RowHeader("List Preference")
        RowPreferences(
            {
                PreferenceList(
                    value = "Value 1",
                    onValueChange = {},
                    items = listOf("Value 1", "Value 2"),
                    itemTextProvider = { it },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                    title = "List Preference",
                    subtitle = "Clicking this item will open a dialog to select a list entry",
                )
            },
            {
                PreferenceList(
                    style = PreferenceList.Style.Spinner,
                    value = "Value 1",
                    onValueChange = {},
                    items = listOf("Value 1", "Value 2"),
                    itemTextProvider = { it },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                    title = "List Preference",
                    subtitle = "Clicking this item will open a dropdown to select a list entry",
                )
            },
            {
                PreferenceListMulti(
                    value = listOf("Value 1", "Value2"),
                    onValueChange = {},
                    items = listOf("Value 1", "Value 2"),
                    itemTextProvider = { it },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                    title = "List Preference",
                    subtitle = "Clicking this item will open a dialog to select one, more or no list entry",
                )
            }
        )
        RowHeader("Number Preference")
        RowPreferences(
            {
                PreferenceNumber(
                    value = 3,
                    onValueChange = {},
                    min = 0,
                    max = 5,
                    stepSize = 1,
                    icon = { Icon(Icons.Default.Star, null) },
                    title = "Input Number Title",
                    subtitle = "This allows you to PICK any valid INTEGER inside a dialog with a number picker",
                )
            },
            {
                PreferenceNumber(
                    style = PreferenceNumber.Style.Slider(),
                    value = 3,
                    onValueChange = {},
                    min = 0,
                    max = 5,
                    stepSize = 1,
                    icon = { Icon(Icons.Default.Star, null) },
                    title = "Input Number Title",
                    subtitle = "Select a number with a slider",
                )
            },
            null
        )
    }
}

@Composable
private fun RowHeader(
    header1: String,
    header2: String = "",
    header3: String = "",
    weight1: Float = 1f,
    weight2: Float = 1f,
    weight3: Float = 1f
) {
    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
        //horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.background
        ) {
            val headers = listOf(header1, header2, header3)
            val weights = listOf(weight1, weight2, weight3)
            for (i in 0..2) {
                if (headers[i].isNotEmpty()) {
                    Text(
                        headers[i],
                        modifier = Modifier
                            .weight(weights[i])
                            .border(1.dp, MaterialTheme.colorScheme.background)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun RowPreferences(
    content1: @Composable PreferenceGroupScope.() -> Unit,
    content2: @Composable (PreferenceGroupScope.() -> Unit)? = null,
    content3: @Composable (PreferenceGroupScope.() -> Unit)? = null
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        //horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(1.dp, MaterialTheme.colorScheme.onBackground)
        ) {
            PreviewPreference {
                content1()
            }
        }
        if (content2 != null) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(1.dp, MaterialTheme.colorScheme.onBackground)
            ) {
                PreviewPreference {
                    content2()
                }
            }
        }
        if (content3 != null) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(1.dp, MaterialTheme.colorScheme.onBackground)
            ) {
                PreviewPreference {
                    content3()
                }
            }
        }
    }
}
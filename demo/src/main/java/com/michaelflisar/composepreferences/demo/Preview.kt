package com.michaelflisar.composepreferences.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
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
import com.michaelflisar.composepreferences.core.PreferenceSectionHeader
import com.michaelflisar.composepreferences.core.composables.PreviewPreference
import com.michaelflisar.composepreferences.core.hierarchy.PreferenceScope
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.button.PreferenceButton
import com.michaelflisar.composepreferences.screen.color.PreferenceColor
import com.michaelflisar.composepreferences.screen.date.PreferenceDate
import com.michaelflisar.composepreferences.screen.list.PreferenceList
import com.michaelflisar.composepreferences.screen.list.PreferenceListMulti
import com.michaelflisar.composepreferences.screen.number.PreferenceNumber
import com.michaelflisar.composepreferences.screen.time.PreferenceTime
import java.time.LocalDate
import java.time.LocalTime

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
                    title = { Text(text = "Info Preference") },
                    subtitle = { Text(text = "This is a description") }
                )
            },
            {
                PreferenceSectionHeader(
                    title = { Text(text = "Section Header") }
                )
            },
            {
                PreferenceSectionHeader(
                    icon = { Icon(Icons.Default.Info, null) },
                    title = { Text(text = "Section Header") },
                    subtitle = { Text(text = "This is a description") }
                )
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
                    title = { Text(text = "Checkbox Title") },
                    subtitle = { Text(text = "This is a description") },
                )
            },
            {
                PreferenceBool(
                    style = PreferenceBool.Style.Switch,
                    value = true,
                    onValueChange = {},
                    icon = { Icon(Icons.Default.Info, null) },
                    title = { Text(text = "Switch Title") },
                    subtitle = { Text(text = "This is a description") },
                )
            },
            {
                PreferenceButton(
                    onClick = {},
                    icon = { Icon(Icons.Default.Share, null) },
                    title = { Text(text = "Button Preference") },
                    subtitle = { Text(text = "Click this item to execute an action") }
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
                    title = { Text(text = "Color Preference") },
                    subtitle = { Text(text = "This preference does allow to select any RGB color") }
                )
            },
            {
                PreferenceColor(
                    value = Color.Red.copy(alpha = .5f),
                    onValueChange = {},
                    icon = { Icon(Icons.Default.Favorite, null) },
                    title = { Text(text = "Color Preference") },
                    subtitle = { Text(text = "This preference does allow to select any ARGB color") }
                )
            },
            null
        )
        RowHeader("Date/Time Preference")
        RowPreferences(
            {
                PreferenceDate(
                    value = LocalDate.now(),
                    onValueChange = {},
                    icon = { Icon(Icons.Default.DateRange, null) },
                    title = { Text(text = "Date Preference") },
                    subtitle = { Text(text = "Click to open a date selector dialog") },
                )
            },
            {
                PreferenceTime(
                    value = LocalTime.now(),
                    onValueChange = {},
                    is24Hours = true,
                    icon = { Icon(Icons.Default.Refresh, null) },
                    title = { Text(text = "Time Title") },
                    subtitle = { Text(text = "Select a time inside a time picker dialog") },
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
                    icon = { Icon(Icons.Default.List, null) },
                    title = { Text(text = "List Preference") },
                    subtitle = { Text(text = "Clicking this item will open a dialog to select a list entry") },
                )
            },
            {
                PreferenceList(
                    style = PreferenceList.Style.Spinner,
                    value = "Value 1",
                    onValueChange = {},
                    items = listOf("Value 1", "Value 2"),
                    itemTextProvider = { it },
                    icon = { Icon(Icons.Default.List, null) },
                    title = { Text(text = "List Preference") },
                    subtitle = { Text(text = "Clicking this item will open a dropdown to select a list entry") },
                )
            },
            {
                PreferenceListMulti(
                    value = listOf("Value 1", "Value2"),
                    onValueChange = {},
                    items = listOf("Value 1", "Value 2"),
                    itemTextProvider = { it },
                    icon = { Icon(Icons.Default.List, null) },
                    title = { Text(text = "List Preference") },
                    subtitle = { Text(text = "Clicking this item will open a dialog to select one, more or no list entry") },
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
                    title = { Text(text = "Input Number Title") },
                    subtitle = { Text(text = "This allows you to PICK any valid INTEGER inside a dialog with a number picker") },
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
                    title = { Text(text = "Input Number Title") },
                    subtitle = { Text(text = "Select a number with a slider") },
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
    content1: @Composable PreferenceScope.() -> Unit,
    content2: @Composable (PreferenceScope.() -> Unit)? = null,
    content3: @Composable (PreferenceScope.() -> Unit)? = null
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
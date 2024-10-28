package com.michaelflisar.composepreferences.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.PreferenceSectionHeader
import com.michaelflisar.composepreferences.core.PreferenceSubScreen
import com.michaelflisar.composepreferences.core.classes.PreferenceFilter
import com.michaelflisar.composepreferences.core.classes.PreferenceSettingsDefaults
import com.michaelflisar.composepreferences.core.classes.PreferenceState
import com.michaelflisar.composepreferences.core.classes.asPreferenceData
import com.michaelflisar.composepreferences.core.classes.rememberPreferenceFilter
import com.michaelflisar.composepreferences.core.classes.rememberPreferenceState
import com.michaelflisar.composepreferences.core.styles.PreferenceStyle
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.button.PreferenceButton
import com.michaelflisar.composepreferences.screen.color.PreferenceColor
import com.michaelflisar.composepreferences.screen.date.PreferenceDate
import com.michaelflisar.composepreferences.screen.input.PreferenceInputNumber
import com.michaelflisar.composepreferences.screen.input.PreferenceInputText
import com.michaelflisar.composepreferences.screen.list.PreferenceList
import com.michaelflisar.composepreferences.screen.list.PreferenceListMulti
import com.michaelflisar.composepreferences.screen.number.PreferenceNumber
import com.michaelflisar.composepreferences.screen.time.PreferenceTime
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun PrefScreenDemoFilter(
    state: PreferenceState = rememberPreferenceState(),
    style: PreferenceStyle,
    snackbarHostState: SnackbarHostState
) {
    val settings = PreferenceSettingsDefaults.settings(
        style = style
    )
    val filter = rememberPreferenceFilter(
        mode = PreferenceFilter.Mode.AllWords,
        highlightSpan = SpanStyle(color = Color.Red),
        flattenResult = true
    )

    val scope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = filter.search.value,
            onValueChange = { filter.search.value = it },
            label = { Text("Search") },
            trailingIcon = if (filter.search.value.isNotEmpty()) {
                {
                    IconButton(onClick = { filter.search.value = "" }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "clear"
                        )
                    }
                }
            } else null
        )

        val bool = remember { mutableStateOf(false) }
        val bool2 = remember { mutableStateOf(false) }
        val color1 = remember { mutableStateOf(Color.Red) }
        val color2 = remember { mutableStateOf(Color.Green) }
        val date1 = remember { mutableStateOf(LocalDate.now().toKotlinLocalDate()) }
        val date2 = remember { mutableStateOf(LocalDate.now().toKotlinLocalDate()) }
        val time1 = remember { mutableStateOf(LocalTime.now().toKotlinLocalTime()) }
        val time2 = remember { mutableStateOf(LocalTime.now().toKotlinLocalTime()) }
        val text1 = remember { mutableStateOf("") }
        val numberInt1 = remember { mutableStateOf(0) }
        val numberFloat1 = remember { mutableStateOf(0f) }
        val numberInt2 = remember { mutableStateOf(0) }
        val numberFloat2 = remember { mutableStateOf(0f) }

        val listItems = List(100) { "Item ${it + 1}"}
        val list1 = remember { mutableStateOf(listItems.first()) }
        val list2 = remember { mutableStateOf(listItems.first()) }
        val listMulti1 = remember { mutableStateOf(emptyList<String>()) }

        PreferenceScreen(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            settings = settings,
            filter = filter,
            state = state
        ) {
            if (state.countCurrentLevel() > 0) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (state.countVisible() == 0) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Empty",
                            textAlign = TextAlign.Center
                        )
                    } else {
                        if (filter.isActive() && filter.flattenResult) {
                            Text("${state.countVisible()} / ${state.countAll(includeGroups = false, includeSections = false)}")
                        } else {
                            Text("${state.countVisible()} / ${state.countCurrentLevel()}")
                        }

                    }


                }
            }

            PreferenceSubScreen(title = "Pref 1 - Core") {
                PreferenceInfo(title = "Pref 1.1")
                PreferenceSubScreen(title = "Pref 1.2 - NESTING") {
                    PreferenceInfo(title = "Pref 1.2.1")
                    PreferenceInfo(title = "Pref 1.2.2")
                    PreferenceSectionHeader(title = "Region 1.2.3") {
                        PreferenceInfo(title = "Pref 1.2.3.1")
                        PreferenceInfo(title = "Pref 1.2.3.2")
                        PreferenceInfo(title = "Pref 1.2.3.3")
                    }
                    PreferenceSubScreen(title = "Pref 1.2.4") {
                        PreferenceInfo(title = "Pref 1.2.4.1")
                        PreferenceInfo(title = "Pref 1.2.4.2")
                    }
                    PreferenceInfo(title = "Pref 1.2.5")
                }
                PreferenceSectionHeader(title = "Pref 1.3 - Section") {
                    PreferenceInfo(title = "Pref 1.3.1")
                    PreferenceInfo(title = "Pref 1.3.2")
                    PreferenceInfo(title = "Pref 1.3.3")
                }
                PreferenceSectionHeader(title = "Pref 1.4 - Section2") {
                    PreferenceInfo(title = "Pref 1.4.1")
                    PreferenceInfo(title = "Pref 1.4.2")
                    PreferenceInfo(title = "Pref 1.4.3")
                }
            }

            PreferenceSubScreen(title = "Pref 2 - Boolean") {
                PreferenceBool(title = "Pref 2.1", data = bool.asPreferenceData())
                PreferenceBool(title = "Pref 2.2", data = bool2.asPreferenceData(), style = PreferenceBool.Style.Checkbox)
            }

            PreferenceSubScreen(title = "Pref 3 - Buttons") {
                PreferenceButton(title = "Pref 3.1 - Button 1", onClick = {
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar("Button 1 clicked")
                    }
                })
            }

            PreferenceSubScreen(title = "Pref 4 - Colors") {
                PreferenceColor(title = "Pref 4.1 - Color 1 - with alpha", data = color1.asPreferenceData())
                PreferenceColor(title = "Pref 4.2 - Color 2 - no alpha", data = color2.asPreferenceData(), alphaSupported = false)
            }

            PreferenceSubScreen(title = "Pref 5 - Date/Time") {
                PreferenceDate(title = "Pref 5.1 - Date 1 (MO - SO)", data = date1.asPreferenceData(), firstDayOfWeek = DayOfWeek.MONDAY)
                PreferenceDate(title = "Pref 5.2 - Date 2 (SO - SA)", data = date2.asPreferenceData(), firstDayOfWeek = DayOfWeek.SUNDAY)
                PreferenceTime(title = "Pref 5.3 - Time (24h)", data = time1.asPreferenceData(), is24Hours = true)
                PreferenceTime(title = "Pref 5.4 - Time (12h)", data = time2.asPreferenceData(), is24Hours = false)
            }

            PreferenceSubScreen(title = "Pref 6 - Input") {
                PreferenceInputText(title = "Pref 6.1 - Text", data = text1.asPreferenceData())
                PreferenceInputNumber(title = "Pref 6.2 - Int", data = numberInt1.asPreferenceData())
                PreferenceInputNumber(title = "Pref 6.3 - Float", data = numberFloat1.asPreferenceData())
            }

            PreferenceSubScreen(title = "Pref 7 - Picker") {
                PreferenceNumber(title = "Pref 7.1 - Int", min = 0, max = 100, stepSize = 1, data = numberInt2.asPreferenceData())
                PreferenceNumber(title = "Pref 7.2 - Float", min = 0f, max = 10f, stepSize = .5f, data = numberFloat2.asPreferenceData(),
                    style = PreferenceNumber.Style.Slider(showTicks = true),
                    formatter = { "%.1f".format(it) }
                )
            }

            PreferenceSubScreen(title = "Pref 8 - List") {
                PreferenceList(title = "Pref 8.1 - List Dialog", data = list1.asPreferenceData(), items = listItems)
                PreferenceList(title = "Pref 8.2 - List Spinner", data = list2.asPreferenceData(), items = listItems, style = PreferenceList.Style.Spinner)
                PreferenceListMulti(title = "Pref 8.1 - Multi Selection", data = listMulti1.asPreferenceData(), items = listItems)
            }

        }
    }
}
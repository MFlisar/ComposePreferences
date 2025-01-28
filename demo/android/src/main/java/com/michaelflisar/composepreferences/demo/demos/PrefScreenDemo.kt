package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.TextSnippet
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartButton
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.PreferenceDivider
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.PreferenceInfoDefaults
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.PreferenceSection
import com.michaelflisar.composepreferences.core.PreferenceSubScreen
import com.michaelflisar.composepreferences.core.classes.Dependency
import com.michaelflisar.composepreferences.core.composables.BasePreference
import com.michaelflisar.composepreferences.core.composables.BasePreferenceContainer
import com.michaelflisar.composepreferences.core.composables.PreferenceItemSetup
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.core.styles.ModernStyle
import com.michaelflisar.composepreferences.core.styles.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.demo.classes.DemoPrefs
import com.michaelflisar.composepreferences.demo.classes.LocalDataStore
import com.michaelflisar.composepreferences.demo.custom.AppItem
import com.michaelflisar.composepreferences.demo.custom.SelectAppPreference
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
import com.michaelflisar.toolbox.classes.ToastHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.format.char
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun PrefScreenDemo() {

    val settings = DemoPrefs.preferenceSettings()

    // Preferences must be wrapped in a screen
    // => this allows to manage internal hierarchy and screen nesting and everything is managed automatically
    // => this also enables internal scrolling
    PreferenceScreen(
        // optional Preferences for this screen...
        settings = settings,
        modifier = Modifier.padding(
            vertical = when (settings.style) {
                is ModernStyle -> 16.dp
                else -> 0.dp
            }
        )
    ) {
        PreferenceSection(
            title = "Demos Section 1"
        ) {
            PreferenceInfoExamples()
            PreferenceBoolExamples()
            PreferenceButtonExamples()
            PreferenceColorExamples()
        }
        PreferenceSection(
            title = "Demos Section 2"
        ) {
            PreferenceDateExamples()
            PreferenceInputExamples()
            PreferenceListExamples()
            PreferenceNumberExamples()
            PreferenceTimeExamples()
            PreferenceDivider()
            PreferenceDependenciesExamples()
            PreferenceDivider()
            PreferenceCustomExamples()
            PreferenceCustom2Examples()
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceInfoExamples() {

    val context = LocalContext.current
    val title = "Info Demos"
    val subtitle = "Click to see some info preference examples"

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.Outlined.Info, null) }
    ) {
        PreferenceSection(
            title = title
        ) {
            PreferenceInfo(
                title = "Info 1 - Version 1",
                subtitle = "This is a preference that only shows some information but does not hold any data. It contains a hidden long click listener that can be used to enabled dev menus/options or similar.",
                icon = { Icon(Icons.Outlined.Info, null) },
                onLongClick = {
                    ToastHelper.show(context, "Hidden long press found :-)")
                }
            )
            PreferenceInfo(
                title = "Info 1 - Version 2",
                subtitle = "This is like the previous information but it aligns its content at the top (all other preferences do always align their content at the center but the info preference allows you to customise this to get something that looks like a footnote preference which is described inside googles preference design guidelines.).",
                icon = { Icon(Icons.Outlined.Info, null) },
                onLongClick = {
                    ToastHelper.show(context, "Hidden long press found :-)")
                },
                itemSetup = PreferenceInfoDefaults.itemSetup().copy(
                    alignment = Alignment.Top
                )
            )
            PreferenceInfo(
                title = "Info 2",
                subtitle = "Another information but with no icon..."
            )
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceBoolExamples() {

    val dataStore = LocalDataStore.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val title = "Bool Demos"
    val subtitle = "Click to see some switch/checkbox preference examples"

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.Default.Check, null) }
    ) {
        PreferenceSection(
            title = title
        ) {
            /* --8<-- [start: demo-bool] */
            val bool1 = dataStore.getBool("bool1", true).collectAsState(initial = true)
            PreferenceBool(
                style = PreferenceBool.Style.Switch,
                value = bool1.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("bool1", it)
                    }
                },
                title = "Bool 1",
                icon = { Icon(Icons.Default.Check, null) }
            )
            /* --8<-- [end: demo-bool] */
            val bool2 = dataStore.getBool("bool2", true).collectAsState(initial = true)
            PreferenceBool(
                style = PreferenceBool.Style.Checkbox,
                value = bool2.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("bool2", it)
                    }
                },
                title = "Bool 2",
                icon = { Icon(Icons.Default.Check, null) }
            )
            val bool3 = dataStore.getBool("bool3", true).collectAsState(initial = true)
            PreferenceBool(
                style = PreferenceBool.Style.Switch,
                value = bool3.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("bool3", it)
                    }
                },
                title = "Bool 3",
                subtitle = "Some description...",
                icon = { Icon(Icons.Default.Info, null) }
            )
            val bool4 = dataStore.getBool("bool4", true).collectAsState(initial = true)
            PreferenceBool(
                style = PreferenceBool.Style.Switch,
                value = bool4.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("bool4", it)
                    }
                },
                title = "Bool 4",
                subtitle = "This one is always disabled",
                icon = { Icon(Icons.Default.Info, null) },
                enabled = Dependency.Disabled
            )
        }
        PreferenceSection(
            title = "No icon example",
            //itemStyle = PreferenceStyleDefaults.primary()
        ) {
            val bool5 = dataStore.getBool("bool5", true).collectAsState(initial = true)
            PreferenceBool(
                style = PreferenceBool.Style.Switch,
                value = bool5.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("bool5", it)
                    }
                },
                title = "Bool 5 - No Icon"
            )
            // Cancel any change via callbak
            val bool6 = dataStore.getBool("bool6", true).collectAsState(initial = true)
            PreferenceBool(
                style = PreferenceBool.Style.Switch,
                value = bool6.value,
                onValueChange = {
                    if (it) {
                        scope.launch(Dispatchers.IO) {
                            dataStore.update("bool5", it)
                        }
                    } else {
                        ToastHelper.show(context, "Change was rejected!")
                    }
                },
                title = "Bool 6 - Can't be changed because onValueChange only accept true"
            )
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceButtonExamples() {

    val context = LocalContext.current
    var counter1 by remember { mutableIntStateOf(0) }
    var counter2 by remember { mutableIntStateOf(0) }
    val title = "Button Demos"
    val subtitle = "Click to see some clickable preferences examples"

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.Default.SmartButton, null) }
    ) {
        PreferenceSection(
            title = title
        ) {
            /* --8<-- [start: demo-button] */
            PreferenceButton(
                title = "Button 1",
                subtitle = "Clicking this button will increase counter 1",
                icon = { Icon(Icons.Default.AdsClick, null) },
                onClick = {
                    ToastHelper.show(context, "Button 1 clicked!")
                    counter1++
                }
            )
            /* --8<-- [end: demo-button] */
            PreferenceButton(
                title = "Button 2",
                subtitle = "Clicking this button will increase counter 2",
                onClick = {
                    ToastHelper.show(context, "Button2 clicked!")
                    counter2++
                }
            )
        }
        PreferenceSection(
            title = "Infos"
        ) {
            PreferenceInfo(
                title = "Counter 1",
                subtitle = "Clicks: $counter1",
                icon = { Icon(Icons.Default.Numbers, null) }
            )
            PreferenceInfo(
                title = "Counter 2",
                subtitle = "Clicks: $counter2",
                icon = { Icon(Icons.Default.Numbers, null) }
            )
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceColorExamples() {

    val dataStore = LocalDataStore.current
    val scope = rememberCoroutineScope()
    val title = "Color Demos"
    val subtitle = "Click to see some color preference examples with optional alpha value support"

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.Default.ColorLens, null) }
    ) {
        PreferenceSection(
            title = title
        ) {

            /* --8<-- [start: demo-color] */
            val color1 = dataStore.getInt("color1", Color.Red.toArgb())
                .collectAsState(initial = Color.Red.toArgb())
            PreferenceColor(
                value = Color(color1.value),
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("color1", it.toArgb())
                    }
                },
                title = "Color 1",
                subtitle = "This preference does support ALPHA values",
                icon = { Icon(Icons.Default.ColorLens, null) }
            )
            /* --8<-- [end: demo-color] */
            val color2 = dataStore.getInt("color2", Color.Green.toArgb())
                .collectAsState(initial = Color.Green.toArgb())
            PreferenceColor(
                value = Color(color2.value),
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("color2", it.toArgb())
                    }
                },
                alphaSupported = false,
                title = "Color 2",
                subtitle = "This preference does only support solid colors",
                icon = { Icon(Icons.Default.ColorLens, null) }
            )
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceDateExamples() {

    val dataStore = LocalDataStore.current
    val scope = rememberCoroutineScope()
    val title = "Date Demos"
    val subtitle = "Click to see some date preference examples"

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.Default.DateRange, null) }
    ) {
        PreferenceSection(
            title = title
        ) {

            /* --8<-- [start: demo-date] */
            val now = LocalDate.now()
            val date1 = dataStore.getLong("date1", now.toEpochDay())
                .collectAsState(initial = now.toEpochDay())
            PreferenceDate(
                value = date1.value.let { LocalDate.ofEpochDay(it).toKotlinLocalDate() },
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("date1", it.toJavaLocalDate().toEpochDay())
                    }
                },
                title = "Date 1",
                subtitle = "First day of week: Monday",
                icon = { Icon(Icons.Default.DateRange, null) }
            )
            /* --8<-- [end: demo-date] */
            val date2 = dataStore.getLong("date2", now.toEpochDay())
                .collectAsState(initial = now.toEpochDay())
            PreferenceDate(
                value = date2.value.let { LocalDate.ofEpochDay(it).toKotlinLocalDate() },
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("date2", it.toJavaLocalDate().toEpochDay())
                    }
                },
                title = "Date 2",
                subtitle = "Custom first day week (sunday) + custom data formatter",
                icon = { Icon(Icons.Default.DateRange, null) },
                firstDayOfWeek = DayOfWeek.SUNDAY,
                formatter = {
                    kotlinx.datetime.LocalDate.Format {
                        year()
                        char('-')
                        monthNumber()
                        char('-')
                        dayOfMonth()
                    }.format(it)
                    //it.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
                }
            )
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceInputExamples() {

    val dataStore = LocalDataStore.current
    val scope = rememberCoroutineScope()
    val title = "Input Demos"
    val subtitle = "Click to see some date input preference examples (texts or numbers)"

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.AutoMirrored.Filled.TextSnippet, null) }
    ) {
        PreferenceSection(
            title = title
        ) {

            PreferenceInfo(
                title = "Supported Formats",
                subtitle = "There is an input preference for strings but also one for numbers (which works with all Number types [Int, Float, Double, Long])",
                icon = { Icon(Icons.Default.Info, null) },
                itemStyle = PreferenceStyleDefaults.primary(),
                itemSetup = PreferenceInfoDefaults.itemSetup().copy(
                    alignment = Alignment.Top,
                    // following allows us to exclude this item from section based styling (color and border style)
                    excludeFromSectionStyle = true
                )
            )

            /* --8<-- [start: demo-input] */
            val input1 = dataStore.getString("input1", "Hello")
                .collectAsState(initial = "Hello")
            PreferenceInputText(
                value = input1.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("input1", it)
                    }
                },
                title = "Input 1",
                subtitle = "String input example",
                icon = { Icon(Icons.AutoMirrored.Filled.TextSnippet, null) }
            )
            /* --8<-- [end: demo-input] */
            /* --8<-- [start: demo-input2] */
            val input2 = dataStore.getInt("input2", 100)
                .collectAsState(initial = 100)
            PreferenceInputNumber(
                value = input2.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("input2", it)
                    }
                },
                title = "Input 2",
                subtitle = "This input preference does only accept valid Int numbers",
                icon = { Icon(Icons.Default.Numbers, null) }
            )
            /* --8<-- [end: demo-input2] */
            val input3 = dataStore.getFloat("input3", 50f)
                .collectAsState(initial = 50f)
            PreferenceInputNumber(
                value = input3.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("input3", it)
                    }
                },
                title = "Input 3",
                subtitle = "This input preference does only accept valid Float numbers",
                icon = { Icon(Icons.Default.Numbers, null) }
            )
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceListExamples() {

    val dataStore = LocalDataStore.current
    val scope = rememberCoroutineScope()
    val title = "List Demos"
    val title2 = "Multi Selection Demos"
    val subtitle = "Click to see some list preference examples (dialogs, dropdowns)"

    val icons = listOf(
        Icons.Default.Info,
        Icons.Default.Numbers,
        Icons.Default.Android,
        Icons.Default.Check,
        Icons.Default.AccessTime,
        Icons.AutoMirrored.Filled.ArrowRight,
        Icons.Default.CheckBox,
        Icons.Default.Key,
        Icons.Default.Accessibility,
        Icons.Default.Alarm
    )
    val list = List(10) { it }

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.AutoMirrored.Filled.List, null) }
    ) {
        PreferenceSection(
            title = title
        ) {
            /* --8<-- [start: demo-list] */
            val list1 = dataStore.getInt("list1", 0)
                .collectAsState(initial = 0)
            PreferenceList(
                style = PreferenceList.Style.Dialog(),
                value = list1.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("list1", it)
                    }
                },
                items = list,
                itemTextProvider = { icons[it].name },
                title = "List 1 (Dialog)",
                icon = { Icon(Icons.AutoMirrored.Filled.List, null) }
            )
            /* --8<-- [end: demo-list] */
            val list2 = dataStore.getInt("list2", 0)
                .collectAsState(initial = 0)
            PreferenceList(
                style = PreferenceList.Style.Spinner,
                value = list2.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("list2", it)
                    }
                },
                items = list,
                itemTextProvider = { icons[it].name },
                title = "List 2 (Spinner)",
                icon = { Icon(Icons.AutoMirrored.Filled.List, null) }
            )
            val list3 = dataStore.getInt("list3", 0)
                .collectAsState(initial = 0)
            PreferenceList(
                style = PreferenceList.Style.Dialog(),
                value = list3.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("list3", it)
                    }
                },
                items = list,
                itemTextProvider = { icons[it].name },
                itemIconProvider = { Icon(icons[it], null) },
                title = "List 3 (Dialog + Icons)",
                icon = { Icon(Icons.AutoMirrored.Filled.List, null) }
            )
            val list4 = dataStore.getInt("list4", 0)
                .collectAsState(initial = 0)
            PreferenceList(
                style = PreferenceList.Style.Spinner,
                value = list4.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("list4", it)
                    }
                },
                items = list,
                itemTextProvider = { icons[it].name },
                itemIconProvider = { Icon(icons[it], null) },
                title = "List 4 (Spinner + Icons)",
                icon = { Icon(Icons.AutoMirrored.Filled.List, null) }
            )
        }
        PreferenceSection(
            title = title2
        ) {
            /* --8<-- [start: demo-list2] */
            val multiList1 = dataStore.getIntList("multiList1", emptyList())
                .collectAsState(initial = emptyList())
            PreferenceListMulti(
                value = multiList1.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("multiList1", it)
                    }
                },
                items = list,
                itemTextProvider = { icons[it].name },
                itemIconProvider = { Icon(icons[it], null) },
                title = "Multi List 1",
                subtitle = "This preference allows you to select multiple items from a list",
                icon = { Icon(Icons.Default.Checklist, null) }
            )
            /* --8<-- [end: demo-list2] */
            val multiList2 = dataStore.getIntList("multiList2", emptyList())
                .collectAsState(initial = emptyList())
            PreferenceListMulti(
                value = multiList2.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("multiList2", it)
                    }
                },
                items = list,
                itemTextProvider = { icons[it].name },
                itemIconProvider = { Icon(icons[it], null) },
                formatter = { selected: List<Int> ->
                    if (selected.isEmpty()) {
                        "No item selected!"
                    } else if (selected.size == 1) {
                        "1 item selected"
                    } else {
                        "${selected.size} items selected"
                    }
                },
                title = "Multi List 2",
                subtitle = "This preference allows you to select multiple items from a list + it provides a custom selected text provider which simply shows the count",
                icon = { Icon(Icons.Default.Checklist, null) }
            )
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceNumberExamples() {

    val dataStore = LocalDataStore.current
    val scope = rememberCoroutineScope()
    val title = "Number Demos"
    val title2 = "Slider examples"
    val subtitle =
        "Click to see some number preference (picker or slider) examples - supports any Number class + supports min/max/stepsize and custom value formatters"

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.AutoMirrored.Filled.TextSnippet, null) }
    ) {
        PreferenceSection(
            title = title
        ) {

            /* --8<-- [start: demo-number] */
            val number1 = dataStore.getInt("number1", 50)
                .collectAsState(initial = 50)
            PreferenceNumber(
                value = number1.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("number1", it)
                    }
                },
                min = 0,
                max = 100,
                stepSize = 1,
                title = "Number 1",
                subtitle = "Select a number in the range [0, 100]",
                icon = { Icon(Icons.Default.Numbers, null) }
            )
            /* --8<-- [end: demo-number] */
            val number2 = dataStore.getInt("number2", 50)
                .collectAsState(initial = 50)
            PreferenceNumber(
                value = number2.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("number2", it)
                    }
                },
                min = 0,
                max = 100,
                stepSize = 10,
                title = "Number 2",
                subtitle = "Select a number in the range [0, 100] in steps of 10",
                icon = { Icon(Icons.Default.Numbers, null) }
            )
        }
        PreferenceSection(title = title2) {

            val slider1 = dataStore.getInt("slider1", 50)
                .collectAsState(initial = 50)
            PreferenceNumber(
                style = PreferenceNumber.Style.Slider(
                    showTicks = false
                ),
                value = slider1.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("slider1", it)
                    }
                },
                min = 0,
                max = 100,
                stepSize = 1,
                title = "Slider 1",
                subtitle = "Select a number in the range [0, 100]",
                icon = { Icon(Icons.Default.Numbers, null) }
            )
            val slider2 = dataStore.getInt("slider2", 50)
                .collectAsState(initial = 50)
            PreferenceNumber(
                style = PreferenceNumber.Style.Slider(
                    showTicks = true
                ),
                value = slider2.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("slider2", it)
                    }
                },
                min = 0,
                max = 100,
                stepSize = 10,
                formatter = { "Selected value = $it" },
                title = "Slider 2",
                subtitle = "Select a number in the range [0, 100] in steps of 10",
                icon = { Icon(Icons.Default.Numbers, null) }
            )
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceTimeExamples() {

    val dataStore = LocalDataStore.current
    val scope = rememberCoroutineScope()
    val title = "Time Demos"
    val subtitle = "Click to see some time preference examples"

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.Default.AccessTime, null) }
    ) {
        PreferenceSection(
            title = title
        ) {
            /* --8<-- [start: demo-time] */
            val now = LocalTime.now()
            val time1 = dataStore.getInt("time1", now.toSecondOfDay())
                .collectAsState(initial = now.toSecondOfDay())
            PreferenceTime(
                value = time1.value.let {
                    LocalTime.ofSecondOfDay(it.toLong()).toKotlinLocalTime()
                },
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("time1", it.toSecondOfDay())
                    }
                },
                title = "Time 1",
                icon = { Icon(Icons.Default.AccessTime, null) }
            )
            /* --8<-- [end: demo-time] */
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceDependenciesExamples() {

    val dataStore = LocalDataStore.current
    val scope = rememberCoroutineScope()
    val title = "Dependency Demos"
    val subtitle =
        "Click to see some examples that show you how you can show/hide or enable/disable preferences dependent on some main preference"

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.Default.Settings, null) }
    ) {
        PreferenceSection(
            title = title
        ) {

            val main1 = dataStore.getBool("main1", true).collectAsState(initial = true)
            /* --8<-- [start: demo-dependency] */
            PreferenceBool(
                style = PreferenceBool.Style.Switch,
                value = main1.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("main1", it)
                    }
                },
                title = "1 MAIN SWITCH",
                subtitle = "This switch does control the enabled states of the next 2 preferences",
                icon = { Icon(Icons.Default.Info, null) },
                itemStyle = PreferenceStyleDefaults.primaryContainer()
            )
            PreferenceInfo(
                title = "Sub Item 1.1...",
                subtitle = "Enabled if main switch is enabled",
                icon = { Icon(Icons.Default.Info, null) },
                enabled = Dependency.State(main1) { it }
            )
            /* --8<-- [end: demo-dependency] */
            PreferenceInfo(
                title = "Sub Item 1.2...",
                subtitle = "Enabled if main switch is disabled",
                icon = { Icon(Icons.Default.Info, null) },
                enabled = Dependency.State(main1) { !it }
            )
            val main2 = dataStore.getBool("main2", true).collectAsState(initial = true)
            PreferenceBool(
                style = PreferenceBool.Style.Switch,
                value = main2.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("main2", it)
                    }
                },
                title = "MAIN SWITCH 2",
                subtitle = "This switch does control the visible states of the next 2 preferences",
                icon = { Icon(Icons.Default.Info, null) },
                itemStyle = PreferenceStyleDefaults.primaryContainer()
            )
            PreferenceInfo(
                title = "Sub Item 2.1...",
                subtitle = "Only visible if parent switch is enabled...",
                icon = { Icon(Icons.Default.Info, null) },
                visible = Dependency.State(main2) { it }
            )
            /* --8<-- [start: demo-dependency2] */
            PreferenceInfo(
                title = "Sub Item 2.2...",
                subtitle = "Only visible if parent switch is enabled...",
                icon = { Icon(Icons.Default.Info, null) },
                visible = Dependency.State(main2) { it }
            )
            /* --8<-- [end: demo-dependency2] */
            PreferenceInfo(
                title = "CUSTOM DEPENDENCIES",
                subtitle = "The second preference does depend on the value of the first preference via some custom logic",
                icon = { Icon(Icons.Default.Info, null) },
                itemStyle = PreferenceStyleDefaults.primaryContainer()
            )
            /* --8<-- [start: demo-dependency3a] */
            val intDependency1 = dataStore.getInt("intDependency1", 0).collectAsState(initial = 0)
            /* --8<-- [end: demo-dependency3a] */
            PreferenceNumber(
                style = PreferenceNumber.Style.Slider(),
                value = intDependency1.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("intDependency1", it)
                    }
                },
                min = 0,
                max = 10,
                stepSize = 1,
                title = "Main Number",
                subtitle = "Select a number >= 5 to enable the next and the second next preference"
            )
            /* --8<-- [start: demo-dependency3b] */
            val intDependency2 = dataStore.getInt("intDependency2", 0).collectAsState(initial = 0)
            PreferenceNumber(
                style = PreferenceNumber.Style.Slider(),
                value = intDependency2.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("intDependency2", it)
                    }
                },
                min = 0,
                max = 10,
                stepSize = 1,
                title = "Sub Number 1",
                subtitle = "Only enabled, if main number >= 5",
                enabled = Dependency.State(intDependency1) {
                    it >= 5
                }
            )
            /* --8<-- [end: demo-dependency3b] */
            val intDependency3 = dataStore.getInt("intDependency3", 0).collectAsState(initial = 0)
            PreferenceNumber(
                style = PreferenceNumber.Style.Slider(),
                value = intDependency3.value,
                onValueChange = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.update("intDependency3", it)
                    }
                },
                min = 0,
                max = 10,
                stepSize = 1,
                title = "Sub Number 2",
                subtitle = "Only visible, if main number >= 5",
                visible = Dependency.State(intDependency1) {
                    it >= 5
                }
            )
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceCustomExamples() {
    val context = LocalContext.current
    val title = "Custom Demos"
    val subtitle =
        "Click to see some custom preference examples (you can either use fully customised content or a title/subtitle/leading/trailing/content based approach that automatically places the provided contents in the same way as all other preferences do it"

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.Default.Android, null) }
    ) {
        PreferenceInfo(
            title = "Important Node",
            subtitle = "You must wrap any composable inside `BasePreference` or `BasePreferenceContainer` because those containers will make sure that visibility of items work correctly (and some more stuff...). Unwrapped composables don't know their hierarchical level and will always be visible!",
            icon = { Icon(Icons.Default.Warning, null) },
            itemStyle = PreferenceStyleDefaults.errorContainer()
        )
        PreferenceSection(
            title = title,
            subtitle = "Possibility 1: BasePreference which offers title/subtitle/icon and content area"
        ) {
            /* --8<-- [start: demo-custom1] */
            BasePreference(
                title = "A custom preference",
                subtitle = "Showing an icon button",
                icon = { Icon(Icons.Default.Android, null) }
            ) {
                IconButton(onClick = {
                    ToastHelper.show(context, "IconButton clicked")
                }) {
                    Icon(Icons.Default.Android, null)
                }
            }
            /* --8<-- [end: demo-custom1] */
            BasePreference(
                title = "A custom preference",
                subtitle = "Showing a button",
                icon = { Icon(Icons.Default.Android, null) }
            ) {
                Button(onClick = {
                    ToastHelper.show(context, "Button clicked")
                }) {
                    Icon(Icons.Default.Android, null)
                }

            }
        }
        PreferenceSection(
            title = title,
            subtitle = "Possibility 2: BasePreferenceContainer which offers a plain content area only for fully custom preferences"
        ) {
            // Information
            PreferenceInfo(
                title = "Important Node",
                subtitle = "`BasePreferenceContainer` is a container that does not apply any styling. You can/must style those preferences yourself.",
                icon = { Icon(Icons.Default.Error, null) },
                itemStyle = PreferenceStyleDefaults.errorContainer(),
                itemSetup = PreferenceItemSetup(
                    // this is no custom preference based on BasePreferenceContainer =>
                    // we want to exclude this item from section based styling (color and border style)
                    excludeFromSectionStyle = true
                )
            )
            /* --8<-- [start: demo-custom2] */
            BasePreferenceContainer(
                modifier = Modifier.padding(16.dp)
            ) { modifier ->
                // you should use the modifier, it handles enabled/disabled state + shaping + animation
                Column(modifier) {
                    Text("A custom preference", fontWeight = FontWeight.Bold, color = Color.Red)
                    Text("Holding a completely customised layout...")
                }
            }
            /* --8<-- [end: demo-custom2] */
            BasePreferenceContainer(
                modifier = Modifier.padding(16.dp),
                // we make sure that the container does not apply and shape clipping, we don't want that
                // if the container holds a card...
                itemStyle = PreferenceStyleDefaults.item()
            ) { modifier ->
                // you should use the modifier, it handles enabled/disabled state + shaping + animation
                // but everything else must be handled by you
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize(),
                    onClick = {
                        ToastHelper.show(context, "Card clicked")
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Info, null)
                        Text(
                            text = "A second custom preference",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            BasePreferenceContainer(
                modifier = Modifier.padding(16.dp),
                itemStyle = PreferenceStyleDefaults.item()
            ) { modifier ->
                Button(
                    modifier = modifier,
                    onClick = {
                        ToastHelper.show(context, "Button clicked")
                    }
                ) {
                    Text("Button")
                }
            }

            BasePreferenceContainer(
                modifier = Modifier.padding(16.dp),
                itemStyle = PreferenceStyleDefaults.item()
            ) { modifier ->
                Row(
                    modifier = modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            ToastHelper.show(context, "Button 1 clicked")
                        }) {
                        Text("Button 1")
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            ToastHelper.show(context, "Button 2 clicked")
                        }) {
                        Text("Button 2")
                    }
                }

            }
        }
    }
}

@Composable
private fun PreferenceScope.PreferenceCustom2Examples() {

    val context = LocalContext.current
    val dataStore = LocalDataStore.current
    val scope = rememberCoroutineScope()
    val title = "Custom Demos 2"
    val subtitle = "Click to see some real world custom examples (app selector)"

    PreferenceSubScreen(
        title = title,
        subtitle = subtitle,
        icon = { Icon(Icons.Default.Apps, null) }
    ) {
        PreferenceSection(
            title = title
        ) {

            val app = dataStore.getString("app", "")
                .collectAsState(initial = "")
            val appItem = remember { mutableStateOf<AppItem?>(null) }
            LaunchedEffect(app.value) {
                withContext(Dispatchers.IO) {
                    appItem.value = SelectAppPreference.loadSingleApp(context, app.value)
                }
            }
            val item = appItem.value
            if (item != null) {
                SelectAppPreference(
                    value = item,
                    onValueChange = {
                        scope.launch(Dispatchers.IO) {
                            dataStore.update("app", it.packageName)
                        }
                    },
                    title = "App",
                    subtitle = "Select a installed app",
                    icon = { Icon(Icons.Default.Apps, null) }
                )
            }
        }
    }
}
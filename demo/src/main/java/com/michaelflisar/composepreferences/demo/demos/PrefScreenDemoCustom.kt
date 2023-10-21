package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.michaelflisar.composepreferences.core.classes.asPreferenceData
import com.michaelflisar.composepreferences.core.classes.asDependency
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.PreferenceDivider
import com.michaelflisar.composepreferences.core.PreferenceSectionHeader
import com.michaelflisar.composepreferences.core.classes.PreferenceSettingsDefaults
import com.michaelflisar.composepreferences.core.classes.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.input.PreferenceInputNumber
import com.michaelflisar.composepreferences.screen.input.PreferenceInputText

@Composable
fun PrefScreenCustomDemo() {

    PreferenceScreen(
        // optional settings for this screen...
        settings = PreferenceSettingsDefaults.settings(
            toggleBooleanOnItemClick = true,
            disabledStateAlpha = .4f,
            disabledStateGrayscale = false,
            maxLinesValue = 2
        )
    ) {
        // settings are normally persisted, so you should use a database or preferences
        // here we will simply use saved remembered state for this showcase
        // HINT: check out the PrefScreenDemoKotPreferences1 example, it uses preferences
        // with my KotPreferences library and shows the easy integration of it...

        val bool1 = rememberSaveable { mutableStateOf(true) }
        val bool2 = rememberSaveable { mutableStateOf(true) }
        val bool3 = rememberSaveable { mutableStateOf(true) }
        val string1 = rememberSaveable { mutableStateOf("Test") }
        val int1 = rememberSaveable { mutableIntStateOf(123) }

        // simple (manual) example
        PreferenceBool(
            style = PreferenceBool.Style.Switch,
            value = bool1.value,
            onValueChange = {
                bool1.value = it
            },
            title = { Text("Bool 1") },
            icon = { Icon(Icons.Default.Check, null) }
        )

        // example with extension function for MutableState<T>
        PreferenceBool(
            style = PreferenceBool.Style.Checkbox,
            data = bool2.asPreferenceData(),
            title = { Text("Bool 2") },
            icon = { Icon(Icons.Default.Check, null) }
        )

        // -----------
        // Master 1
        // -----------

        val master1Data = bool3.asPreferenceData()
        val master1Dependency = bool3.asDependency(enabled = { it })

        PreferenceBool(
            style = PreferenceBool.Style.Switch,
            data = master1Data,
            title = { Text("Master 1") },
            preferenceStyle = PreferenceStyleDefaults.primaryContainer()
        )
        PreferenceSectionHeader(
            title = { Text("Master 1") },
            subtitle = { Text("Region header with sub title...") },
            visible = master1Dependency
        )
        PreferenceDivider(visible = master1Dependency)
        PreferenceInputText(
            data = string1.asPreferenceData(),
            title = { Text("Text 1") },
            subtitle = { Text("Description Text 1") },
            icon = { Icon(Icons.Default.TextSnippet, null) },
            visible = master1Dependency
        )
        PreferenceInputNumber(
            data = int1.asPreferenceData(),
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Number 1")
                    Text(
                        "PRO",
                        modifier = Modifier
                            .border(2.dp, Color.Red, MaterialTheme.shapes.extraSmall)
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        color = Color.Red,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            subtitle = { Text("Value = ${int1.value}") },
            icon = { Icon(Icons.Default.Numbers, null) },
            visible = master1Dependency
        )
    }
}
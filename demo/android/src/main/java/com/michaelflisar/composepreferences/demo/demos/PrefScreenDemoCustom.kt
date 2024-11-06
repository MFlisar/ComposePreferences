package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TextSnippet
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.PreferenceDivider
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.PreferenceSectionHeader
import com.michaelflisar.composepreferences.core.classes.PreferenceSettingsDefaults
import com.michaelflisar.composepreferences.core.classes.asDependency
import com.michaelflisar.composepreferences.core.styles.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.demo.classes.DemoPrefs
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.input.PreferenceInputNumber
import com.michaelflisar.composepreferences.screen.input.PreferenceInputText

@Composable
fun PrefScreenCustomDemo() {

    val settings = DemoPrefs.preferenceSettings()

    PreferenceScreen(
        settings = settings,
        modifier = Modifier.padding(16.dp)
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
            title = "Bool 1",
            icon = { Icon(Icons.Default.Check, null) }
        )

        // example with extension function for MutableState<T>
        PreferenceBool(
            style = PreferenceBool.Style.Checkbox,
            value = bool2,
            title = "Bool 2",
            icon = { Icon(Icons.Default.Check, null) }
        )

        // -----------
        // Master 1
        // -----------

        val master1Data = bool3
        val master1Dependency = bool3.asDependency(enabled = { it })

        PreferenceBool(
            style = PreferenceBool.Style.Switch,
            value = master1Data,
            title = "Master 1",
            itemStyle = PreferenceStyleDefaults.primaryContainer()
        )
        PreferenceSectionHeader(
            title = "Master 1",
            subtitle = "Region header with sub title...",
            visible = master1Dependency
        ) {
            //PreferenceDivider(visible = master1Dependency)
            PreferenceInputText(
                value = string1,
                title = "Text 1",
                subtitle = "Description Text 1",
                icon = { Icon(Icons.AutoMirrored.Filled.TextSnippet, null) },
                visible = master1Dependency
            )
            PreferenceInputNumber(
                value = int1,
                title = "NUMBER (PRO FEATURE)",
                itemStyle = PreferenceStyleDefaults.error(),
                /*
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
                },*/
                subtitle = "Value = ${int1.intValue}",
                icon = { Icon(Icons.Default.Numbers, null) },
                visible = master1Dependency
            )
        }
    }
}
package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TextSnippet
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.PreferenceSection
import com.michaelflisar.composepreferences.core.classes.PreferenceSettings
import com.michaelflisar.composepreferences.core.classes.asDependency
import com.michaelflisar.composepreferences.core.classes.rememberPreferenceState
import com.michaelflisar.composepreferences.core.styles.ModernStyle
import com.michaelflisar.composepreferences.core.styles.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.demo.composables.PreferenceNavigationAndInfo
import com.michaelflisar.composepreferences.demo.preferences.DemoPrefs1
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.input.PreferenceInputNumber
import com.michaelflisar.composepreferences.screen.input.PreferenceInputNumberDefaults
import com.michaelflisar.composepreferences.screen.input.PreferenceInputText

@Composable
fun PrefScreenCustomDemo(
    settings: PreferenceSettings,
) {
    val state = rememberPreferenceState()

    Column {
        PreferenceNavigationAndInfo(state)
        PreferenceScreen(
            settings = settings,
            modifier = Modifier.padding(
                vertical = when (settings.style) {
                    is ModernStyle -> 16.dp
                    else -> 0.dp
                }
            ),
            state = state
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

            PreferenceSection(
                title = "Boolean"
            ) {

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
            }

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
            PreferenceSection(
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
                val isProVersion = remember { mutableStateOf(false) }
                PreferenceInputNumber(
                    value = int1,
                    title = "Pro Feature",
                    enabled = isProVersion.asDependency { it },
                    itemSetup = PreferenceInputNumberDefaults.itemSetup(),
                    titleRenderer = { title ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(title)
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
                    subtitle = "Value = ${int1.intValue}",
                    icon = { Icon(Icons.Default.Numbers, null) },
                    visible = master1Dependency
                )
            }
        }
    }
}
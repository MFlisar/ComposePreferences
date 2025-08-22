package com.michaelflisar.composepreferences.demo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.styles.ModernStyle
import com.michaelflisar.composepreferences.demo.classes.DemoStyle
import com.michaelflisar.composepreferences.demo.demos.PrefScreenCustomDemo
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemo
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemoFilter
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemoKotPreferences
import com.michaelflisar.composepreferences.demo.preferences.DemoPrefs1
import com.michaelflisar.composepreferences.demo.preferences.DemoPrefs2
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.list.PreferenceList
import com.michaelflisar.composepreferences.screen.number.PreferenceNumber
import com.michaelflisar.democomposables.examples.Demo
import com.michaelflisar.democomposables.examples.DemoExamplesContent
import com.michaelflisar.democomposables.examples.DemoExamplesList
import com.michaelflisar.democomposables.examples.rememberSelectedDemo
import com.michaelflisar.democomposables.layout.DemoCollapsibleRegion
import com.michaelflisar.democomposables.layout.DemoColumn
import com.michaelflisar.democomposables.layout.rememberDemoExpandedRegions
import com.michaelflisar.kotpreferences.compose.asMutableStateNotNull

@Composable
fun DemoContent(
    modifier: Modifier,
    showInfo: (info: String) -> Unit,
    prefs1: DemoPrefs1,
    prefs2: DemoPrefs2,
    dataStore: DemoDataStore
) {
    // ------------
    // demos
    // ------------

    val demos = listOf(
        Demo.Example("Demo") { PrefScreenDemo(prefs1.preferenceSettings(), dataStore, showInfo) },
        Demo.Example("Demo (Custom)") { PrefScreenCustomDemo(prefs1.preferenceSettings()) },
        Demo.Example("Demo (KotPreferences)") { PrefScreenDemoKotPreferences(prefs1.preferenceSettings(), prefs2) },
        Demo.Divider,
        Demo.Example("Filter") { PrefScreenDemoFilter(prefs1.preferenceSettings(), showInfo) },
    )

    // ------------
    // UI
    // ------------

    val selectedDemo = rememberSelectedDemo()
    val collapsibleRegion = rememberDemoExpandedRegions(ids = listOf(1, 2), single = false)
    DemoColumn(
        modifier = modifier.fillMaxSize()
            .then(
                if (selectedDemo.intValue == -1) Modifier.verticalScroll(rememberScrollState())
                else Modifier
            )
    ) {
        if (selectedDemo.intValue == -1) {
            // LEVEL 0 - settings + demo list
            DemoCollapsibleRegion(
                title = "Settings",
                regionId = 1,
                state = collapsibleRegion,
                info = "Those settings are used for all demos in this demo app!",
            ) {
                val settings = prefs1.preferenceSettings(
                    style = ModernStyle.create(
                        outerPadding = PaddingValues(0.dp)
                    ),
                    animationSpec = null
                )

                PreferenceScreen(
                    scrollable = false,
                    settings = settings
                ) {
                    PreferenceList(
                        value = prefs1.style.asMutableStateNotNull(),
                        title = "Style",
                        items = DemoStyle.entries.toList(),
                        icon = { Icon(Icons.Default.Style, null) },
                    )
                    PreferenceNumber(
                        value = prefs1.disabledStateAlpha.asMutableStateNotNull(),
                        title = "Alpha",
                        subtitle = "Alpha value for disabled preferences",
                        icon = { Icon(Icons.Default.Settings, null) },
                        min = 0f,
                        max = 1f,
                        stepSize = 0.1f,
                        formatter = { value ->
                            val rounded = (value * 10).toInt() / 10f
                            val str = rounded.toString()
                            if (str.contains(".")) {
                                val parts = str.split(".")
                                parts[0] + "." + parts[1].padEnd(1, '0').take(1)
                            } else {
                                "$str.0"
                            }
                        }
                    )
                    PreferenceBool(
                        style = PreferenceBool.Style.Switch,
                        value = prefs1.disabledStateGrayscale.asMutableStateNotNull(),
                        title = "Grayscale",
                        subtitle = "Grayscale out disabled preferences?",
                        icon = { Icon(Icons.Default.Settings, null) }
                    )
                    PreferenceBool(
                        style = PreferenceBool.Style.Switch,
                        value = prefs1.toggleBooleanOnItemClick.asMutableStateNotNull(),
                        title = "Bool Behaviour",
                        subtitle = "Toggle boolean preferences on item click (or on checkbox/switch click only)?",
                        icon = { Icon(Icons.Default.Settings, null) }
                    )
                    PreferenceNumber(
                        value = prefs1.maxLinesValue.asMutableStateNotNull(),
                        title = "Lines",
                        subtitle = "Maximum number of lines for all preferences that show some text inside their content area.",
                        icon = { Icon(Icons.Default.Settings, null) },
                        min = 0,
                        max = 5,
                        stepSize = 1
                    )
                    PreferenceBool(
                        style = PreferenceBool.Style.Switch,
                        value = prefs1.showSubScreenEndIndicator.asMutableStateNotNull(),
                        title = "Sub Screen Indicator",
                        subtitle = "Show an arrow on the right for sub screens?",
                        icon = { Icon(Icons.Default.Settings, null) }
                    )
                    PreferenceBool(
                        style = PreferenceBool.Style.Switch,
                        value = prefs1.forceNoIconInset.asMutableStateNotNull(),
                        title = "No Icon Inset",
                        subtitle = "Force an inset for preferences without an icon, so that all preferences align beautifully on the left?",
                        icon = { Icon(Icons.Default.Settings, null) }
                    )
                }
            }
            DemoCollapsibleRegion("Demos", 2, collapsibleRegion) {
                DemoExamplesList(selectedDemo, demos)
            }
        } else {
            // LEVEL 1 CONTENT - selected demo page
            DemoExamplesContent(selectedDemo, demos)
        }
    }
}


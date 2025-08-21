package com.michaelflisar.composepreferences.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import com.michaelflisar.composedialogs.core.DialogDefaults
import com.michaelflisar.composepreferences.core.filter.DefaultPreferenceFilter
import com.michaelflisar.composepreferences.core.filter.rememberDefaultPreferenceFilter
import com.michaelflisar.composepreferences.core.styles.DefaultStyle
import com.michaelflisar.composepreferences.core.styles.ModernStyle
import com.michaelflisar.composepreferences.demo.classes.DemoStyle
import com.michaelflisar.composepreferences.demo.composables.PreferenceSettings
import com.michaelflisar.composepreferences.demo.demos.PrefScreenCustomDemo
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemo
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemoFilter
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemoKotPreferences
import com.michaelflisar.composepreferences.demo.preferences.DemoPrefs1
import com.michaelflisar.composepreferences.demo.preferences.DemoPrefs2
import com.michaelflisar.democomposables.DemoSegmentedControl
import com.michaelflisar.democomposables.examples.Demo
import com.michaelflisar.democomposables.examples.DemoExamplesContent
import com.michaelflisar.democomposables.examples.DemoExamplesList
import com.michaelflisar.democomposables.examples.rememberSelectedDemo
import com.michaelflisar.democomposables.layout.DemoCollapsibleRegion
import com.michaelflisar.democomposables.layout.DemoColumn
import com.michaelflisar.democomposables.layout.rememberDemoExpandedRegions

@Composable
fun DemoContent(
    modifier: Modifier,
    showInfo: (info: String) -> Unit,
    prefs1: DemoPrefs1,
    prefs2: DemoPrefs2,
    dataStore: DemoDataStore
) {
    // ------------
    // settings
    // ------------

    val demoStyle = rememberSaveable { mutableStateOf(DemoStyle.Modern) }
    val style = if (demoStyle.value == DemoStyle.Modern) {
        ModernStyle.create(
            //cornerSize = 16.dp,
            //sectionForegroundColor = Color.Red,
            //sectionGroupItemForegroundColor = Color.White,
            //sectionGroupItemBackgroundColor = Color.Black
        )
    } else {
        DefaultStyle.create(
            //backgroundColor = Color.Green,
            //foregroundColor = Color.Yellow,
            //sectionBackgroundColor = Color.Blue,
            sectionForegroundColor = Color.Red
        )
    }

    val settings = prefs1.preferenceSettings(style)

    // ------------
    // demos
    // ------------

    val demos = listOf(
        Demo.Example("Demo") { PrefScreenDemo(settings, dataStore, showInfo) },
        Demo.Example("Demo (Custom)") { PrefScreenCustomDemo(settings) },
        Demo.Example("Demo (KotPreferences)") { PrefScreenDemoKotPreferences(settings, prefs2) },
        Demo.Divider,
        Demo.Example("Filter") { PrefScreenDemoFilter(settings, showInfo) },
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
            DemoCollapsibleRegion("Settings", 1, collapsibleRegion) {
                DemoColumn {
                    DemoSegmentedControl(state = demoStyle, items = DemoStyle.entries)
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


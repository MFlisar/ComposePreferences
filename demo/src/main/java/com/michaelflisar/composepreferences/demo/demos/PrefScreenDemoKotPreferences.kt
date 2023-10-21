package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.michaelflisar.composepreferences.core.PreferenceDivider
import com.michaelflisar.composepreferences.core.PreferenceSectionHeader
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.PreferenceSubScreen
import com.michaelflisar.composepreferences.core.classes.PreferenceSettingsDefaults
import com.michaelflisar.composepreferences.core.classes.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.demo.classes.DemoPrefs
import com.michaelflisar.composepreferences.kotpreferences.asDependency
import com.michaelflisar.composepreferences.kotpreferences.asPreferenceData
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.input.PreferenceInputNumber
import com.michaelflisar.composepreferences.screen.input.PreferenceInputText

@Composable
fun PrefScreenDemoKotPreferences1() {

    PreferenceScreen(
        // optional settings for this screen...
        settings = PreferenceSettingsDefaults.settings(
            toggleBooleanOnItemClick = true,
            disabledStateAlpha = .4f,
            disabledStateGrayscale = false,
            maxLinesValue = 2
        )
    ) {

        // -----------
        // Master 1
        // -----------

        PreferenceBool(
            style = PreferenceBool.Style.Switch,
            data = DemoPrefs.master1.asPreferenceData(),
            title = { Text("Master 1") },
            subtitle = { Text("This will enabled/disable all sub items") },
            icon = { Icon(Icons.Default.Group, null) },
            preferenceStyle = PreferenceStyleDefaults.primaryContainer()
        )
        PreferenceSectionHeader(
            title = { Text("Master 1") },
            enabled = DemoPrefs.master1.asDependency { it }
        )
        PreferenceDivider(enabled = DemoPrefs.master1.asDependency { it })
        PreferenceInputNumber(
            data = DemoPrefs.node1a.asPreferenceData(),
            enabled = DemoPrefs.master1.asDependency { it },
            title = { Text("1a) Node") },
            subtitle = { Text("Sub Item Master1") },
            icon = { Icon(Icons.Default.Numbers, null) }
        )
        PreferenceBool(
            style = PreferenceBool.Style.Switch,
            data = DemoPrefs.node1b.asPreferenceData(),
            enabled = DemoPrefs.master1.asDependency { it },
            title = { Text("1b) Node") },
            subtitle = { Text("Sub Item Master1") },
            icon = { Icon(Icons.Default.Check, null) }
        )
        PreferenceInputText(
            data = DemoPrefs.node1c.asPreferenceData(),
            enabled = DemoPrefs.master1.asDependency { it },
            title = { Text("1c) Node") },
            subtitle = { Text("Sub Item Master1") },
            icon = { Icon(Icons.Default.TextSnippet, null) }
        )

        // -----------
        // Master 2
        // -----------

        PreferenceBool(
            style = PreferenceBool.Style.Checkbox,
            data = DemoPrefs.master2.asPreferenceData(),
            title = { Text("Master 2") },
            subtitle = { Text("This will show/hide all sub items") },
            icon = { Icon(Icons.Default.Group, null) },
            preferenceStyle = PreferenceStyleDefaults.primaryContainer()
        )
        PreferenceSectionHeader(
            title = { Text("Master 2") },
            visible = DemoPrefs.master2.asDependency { it }
        )
        PreferenceDivider(
            visible = DemoPrefs.master2.asDependency { it }
        )
        PreferenceInputNumber(
            data = DemoPrefs.node2a.asPreferenceData(),
            visible = DemoPrefs.master2.asDependency { it },
            title = { Text("2a) Node") },
            subtitle = { Text("Sub Item of Master2") },
            icon = { Icon(Icons.Default.Numbers, null) }
        )
        PreferenceBool(
            style = PreferenceBool.Style.Checkbox,
            data = DemoPrefs.node2b.asPreferenceData(),
            visible = DemoPrefs.master2.asDependency { it },
            title = { Text("2b) Node") },
            subtitle = { Text("Sub Item Master2") },
            icon = { Icon(Icons.Default.Check, null) }
        )
        PreferenceInputText(
            data = DemoPrefs.node2c.asPreferenceData(),
            visible = DemoPrefs.master2.asDependency { it },
            title = { Text("2c) Node") },
            subtitle = { Text("Sub Item Master2") },
            icon = { Icon(Icons.Default.TextSnippet, null) }
        )

        // -----------
        // Sub Screen
        // -----------

        PreferenceSubScreen(
            title = { Text("Sub Screen") },
            subtitle = { Text("Opens a sub screen...") },
            icon = { Icon(Icons.Default.DoubleArrow, null) }
        ) {
            PreferenceInputNumber(
                data = DemoPrefs.intValue.asPreferenceData(),
                title = { Text("Int Value") },
                icon = { Icon(Icons.Default.Numbers, null) }
            )
            PreferenceSubScreen(
                title = { Text("Sub Sub Screen") },
                subtitle = { Text("Opens a sub sub screen...") },
                icon = { Icon(Icons.Default.DoubleArrow, null) }
            ) {
                PreferenceInputNumber(
                    data = DemoPrefs.intValue.asPreferenceData(),
                    title = { Text("Int Value") },
                    icon = { Icon(Icons.Default.Numbers, null) }
                )
            }
        }
    }
}
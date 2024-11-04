package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TextSnippet
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.PreferenceDivider
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.PreferenceSectionHeader
import com.michaelflisar.composepreferences.core.PreferenceSubScreen
import com.michaelflisar.composepreferences.core.classes.PreferenceSettingsDefaults
import com.michaelflisar.composepreferences.core.styles.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.demo.classes.Demo2Prefs
import com.michaelflisar.composepreferences.kotpreferences.asDependency
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.input.PreferenceInputNumber
import com.michaelflisar.composepreferences.screen.input.PreferenceInputText
import com.michaelflisar.kotpreferences.compose.asMutableState

@Composable
fun PrefScreenDemoKotPreferences1() {

    PreferenceScreen(
        // optional settings for this screen...
        settings = PreferenceSettingsDefaults.settings(
            toggleBooleanOnItemClick = true,
            disabledStateAlpha = .4f,
            disabledStateGrayscale = false,
            maxLinesValue = 2
        ),
        modifier = Modifier.padding(16.dp)
    ) {

        // -----------
        // Master 1
        // -----------

        PreferenceBool(
            style = PreferenceBool.Style.Switch,
            value = Demo2Prefs.master1.asMutableState(),
            title = "Master 1",
            subtitle = "This will enabled/disable all sub items",
            icon = { Icon(Icons.Default.Group, null) },
            itemStyle = PreferenceStyleDefaults.primaryContainer()
        )
        PreferenceSectionHeader(
            title = "Master 1",
            enabled = Demo2Prefs.master1.asDependency { it }
        ) {
            PreferenceDivider(enabled = Demo2Prefs.master1.asDependency { it })
            PreferenceInputNumber(
                value = Demo2Prefs.node1a.asMutableState(),
                enabled = Demo2Prefs.master1.asDependency { it },
                title = "1a) Node",
                subtitle = "Sub Item Master1",
                icon = { Icon(Icons.Default.Numbers, null) }
            )
            PreferenceBool(
                style = PreferenceBool.Style.Switch,
                value = Demo2Prefs.node1b.asMutableState(),
                enabled = Demo2Prefs.master1.asDependency { it },
                title = "1b) Node",
                subtitle = "Sub Item Master1",
                icon = { Icon(Icons.Default.Check, null) }
            )
            PreferenceInputText(
                value = Demo2Prefs.node1c.asMutableState(),
                enabled = Demo2Prefs.master1.asDependency { it },
                title = "1c) Node",
                subtitle = "Sub Item Master1",
                icon = { Icon(Icons.AutoMirrored.Filled.TextSnippet, null) }
            )
        }
        // -----------
        // Master 2
        // -----------

        PreferenceBool(
            style = PreferenceBool.Style.Checkbox,
            value = Demo2Prefs.master2.asMutableState(),
            title = "Master 2",
            subtitle = "This will show/hide all sub items",
            icon = { Icon(Icons.Default.Group, null) },
            itemStyle = PreferenceStyleDefaults.primaryContainer()
        )
        PreferenceSectionHeader(
            title = "Master 2",
            visible = Demo2Prefs.master2.asDependency { it }
        ) {
            PreferenceDivider(
                visible = Demo2Prefs.master2.asDependency { it }
            )
            PreferenceInputNumber(
                value = Demo2Prefs.node2a.asMutableState(),
                visible = Demo2Prefs.master2.asDependency { it },
                title = "2a) Node",
                subtitle = "Sub Item of Master2",
                icon = { Icon(Icons.Default.Numbers, null) }
            )
            PreferenceBool(
                style = PreferenceBool.Style.Checkbox,
                value = Demo2Prefs.node2b.asMutableState(),
                visible = Demo2Prefs.master2.asDependency { it },
                title = "2b) Node",
                subtitle = "Sub Item Master2",
                icon = { Icon(Icons.Default.Check, null) }
            )
            PreferenceInputText(
                value = Demo2Prefs.node2c.asMutableState(),
                visible = Demo2Prefs.master2.asDependency { it },
                title = "2c) Node",
                subtitle = "Sub Item Master2",
                icon = { Icon(Icons.AutoMirrored.Filled.TextSnippet, null) }
            )
        }

        // -----------
        // Sub Screen
        // -----------

        PreferenceSubScreen(
            title = "Sub Screen",
            subtitle = "Opens a sub screen...",
            icon = { Icon(Icons.Default.DoubleArrow, null) }
        ) {
            PreferenceInputNumber(
                value = Demo2Prefs.intValue.asMutableState(),
                title = "Int Value",
                icon = { Icon(Icons.Default.Numbers, null) }
            )
            PreferenceSubScreen(
                title = "Sub Sub Screen",
                subtitle = "Opens a sub sub screen...",
                icon = { Icon(Icons.Default.DoubleArrow, null) }
            ) {
                PreferenceInputNumber(
                    value = Demo2Prefs.intValue.asMutableState(),
                    title = "Int Value",
                    icon = { Icon(Icons.Default.Numbers, null) }
                )
            }
        }
    }
}
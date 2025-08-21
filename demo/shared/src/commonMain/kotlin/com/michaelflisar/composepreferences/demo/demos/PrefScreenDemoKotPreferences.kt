package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.foundation.layout.Column
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
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.PreferenceSection
import com.michaelflisar.composepreferences.core.PreferenceSubScreen
import com.michaelflisar.composepreferences.core.classes.PreferenceSettings
import com.michaelflisar.composepreferences.core.classes.rememberPreferenceState
import com.michaelflisar.composepreferences.core.styles.ModernStyle
import com.michaelflisar.composepreferences.core.styles.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.demo.composables.PreferenceNavigationAndInfo
import com.michaelflisar.composepreferences.demo.preferences.DemoPrefs2
import com.michaelflisar.composepreferences.kotpreferences.asDependency
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.input.PreferenceInputNumber
import com.michaelflisar.composepreferences.screen.input.PreferenceInputText
import com.michaelflisar.kotpreferences.compose.asMutableStateNotNull

@Composable
fun PrefScreenDemoKotPreferences(
    settings: PreferenceSettings,
    prefs2: DemoPrefs2
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

            // -----------
            // Master 1
            // -----------

            PreferenceBool(
                style = PreferenceBool.Style.Switch,
                value = prefs2.master1.asMutableStateNotNull(),
                title = "Master 1",
                subtitle = "This will enabled/disable all sub items",
                icon = { Icon(Icons.Default.Group, null) },
                itemStyle = PreferenceStyleDefaults.primaryContainer()
            )
            PreferenceSection(
                title = "Master 1",
                enabled = prefs2.master1.asDependency { it }
            ) {
                //PreferenceDivider(enabled = prefs2.master1.asDependency { it })
                PreferenceInputNumber(
                    value = prefs2.node1a.asMutableStateNotNull(),
                    enabled = prefs2.master1.asDependency { it },
                    title = "1a) Node",
                    subtitle = "Sub Item Master1",
                    icon = { Icon(Icons.Default.Numbers, null) }
                )
                PreferenceBool(
                    style = PreferenceBool.Style.Switch,
                    value = prefs2.node1b.asMutableStateNotNull(),
                    enabled = prefs2.master1.asDependency { it },
                    title = "1b) Node",
                    subtitle = "Sub Item Master1",
                    icon = { Icon(Icons.Default.Check, null) }
                )
                PreferenceInputText(
                    value = prefs2.node1c.asMutableStateNotNull(),
                    enabled = prefs2.master1.asDependency { it },
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
                value = prefs2.master2.asMutableStateNotNull(),
                title = "Master 2",
                subtitle = "This will show/hide all sub items",
                icon = { Icon(Icons.Default.Group, null) },
                itemStyle = PreferenceStyleDefaults.primaryContainer()
            )
            PreferenceSection(
                title = "Master 2",
                visible = prefs2.master2.asDependency { it }
            ) {
                //PreferenceDivider(visible = prefs2.master2.asDependency { it })
                PreferenceInputNumber(
                    value = prefs2.node2a.asMutableStateNotNull(),
                    visible = prefs2.master2.asDependency { it },
                    title = "2a) Node",
                    subtitle = "Sub Item of Master2",
                    icon = { Icon(Icons.Default.Numbers, null) }
                )
                PreferenceBool(
                    style = PreferenceBool.Style.Checkbox,
                    value = prefs2.node2b.asMutableStateNotNull(),
                    visible = prefs2.master2.asDependency { it },
                    title = "2b) Node",
                    subtitle = "Sub Item Master2",
                    icon = { Icon(Icons.Default.Check, null) }
                )
                PreferenceInputText(
                    value = prefs2.node2c.asMutableStateNotNull(),
                    visible = prefs2.master2.asDependency { it },
                    title = "2c) Node",
                    subtitle = "Sub Item Master2",
                    icon = { Icon(Icons.AutoMirrored.Filled.TextSnippet, null) }
                )
            }

            // -----------
            // Sub Screen
            // -----------

            PreferenceSection(
                title = "Sub Screen",
                visible = prefs2.master2.asDependency { it }
            ) {
                PreferenceSubScreen(
                    title = "Sub Screen",
                    subtitle = "Opens a sub screen...",
                    icon = { Icon(Icons.Default.DoubleArrow, null) }
                ) {
                    PreferenceSection(
                        title = "Sub Screen"
                    ) {
                        PreferenceInputNumber(
                            value = prefs2.intValue.asMutableStateNotNull(),
                            title = "Int Value",
                            icon = { Icon(Icons.Default.Numbers, null) }
                        )
                        PreferenceSubScreen(
                            title = "Sub Sub Screen",
                            subtitle = "Opens a sub sub screen...",
                            icon = { Icon(Icons.Default.DoubleArrow, null) }
                        ) {
                            PreferenceSection(
                                title = "Sub Sub Screen"
                            ) {
                                PreferenceInputNumber(
                                    value = prefs2.intValue.asMutableStateNotNull(),
                                    title = "Int Value",
                                    icon = { Icon(Icons.Default.Numbers, null) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
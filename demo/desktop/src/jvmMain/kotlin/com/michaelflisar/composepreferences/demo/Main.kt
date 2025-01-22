package com.michaelflisar.composepreferences.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.michaelflisar.composepreferences.core.classes.PreferenceState
import com.michaelflisar.composepreferences.core.classes.rememberPreferenceState
import com.michaelflisar.composepreferences.core.composables.PreferenceItemDefaults
import com.michaelflisar.composepreferences.core.styles.DefaultStyle
import com.michaelflisar.composepreferences.core.styles.ModernStyle
import com.michaelflisar.composepreferences.core.styles.PreferenceStyle
import com.michaelflisar.toolbox.components.MyIconButton
import com.michaelflisar.toolbox.components.MyTitle

fun main() {
    application {
        Window(
            title = "ComposePreferences Demo",
            onCloseRequest = ::exitApplication,
            state = rememberWindowState(
                position = WindowPosition(Alignment.Center),
                width = 800.dp,
                height = 600.dp
            )
        ) {
            val snackbarHostState = remember { SnackbarHostState() }
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { SnackbarHost(snackbarHostState) },
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MyTitle("Default Style")
                        Preferences(
                            DefaultStyle.create(
                                //backgroundColor = Color.Green,
                                //foregroundColor = Color.Yellow,
                                //sectionBackgroundColor = Color.Blue,
                                sectionForegroundColor = Color.Red
                            ),
                            snackbarHostState
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MyTitle("Modern Style")
                        Preferences(
                            ModernStyle.create(
                                //cornerSize = 16.dp,
                                //sectionForegroundColor = Color.Red,
                                //sectionGroupItemForegroundColor = Color.White,
                                //sectionGroupItemBackgroundColor = Color.Black
                            ), snackbarHostState
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Preferences(style: PreferenceStyle, snackbarHostState: SnackbarHostState) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val state = rememberPreferenceState()

        // Navigation + Infos
        PreferenceNavigationAndInfo(state)

        // Preferences
        PrefScreenDemoFilter(state = state, style = style, snackbarHostState = snackbarHostState)
    }
}

@Composable
private fun PreferenceNavigationAndInfo(state: PreferenceState) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MyIconButton(
            icon = Icons.AutoMirrored.Filled.ArrowLeft,
            enabled = state.currentLevel != 0
        ) {
            state.popLast()
        }
        Text(text = "state.currentLevel = ${state.currentLevel}", modifier = Modifier.weight(1f))
    }
}
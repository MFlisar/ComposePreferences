package com.michaelflisar.composepreferences.demo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.michaelflisar.composepreferences.demo.preferences.DemoDataStore
import com.michaelflisar.composepreferences.demo.preferences.DemoPrefs1
import com.michaelflisar.composepreferences.demo.preferences.DemoPrefs2
import com.michaelflisar.democomposables.DemoScaffold
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create
import okio.Path.Companion.toOkioPath
import java.io.File

fun main() {
    application {

        val appFolder = File(System.getProperty("user.dir"))

        val prefs1 = DemoPrefs1(DataStoreStorage.create(folder = appFolder, name = "demo_prefs1"))
        val prefs2 = DemoPrefs2(DataStoreStorage.create(folder = appFolder, name = "demo_prefs2"))
        val dataStore = DemoDataStore(
            PreferenceDataStoreFactory.createWithPath(
                produceFile = { File(appFolder, "demo_data_store.preferences_pb").toOkioPath() }
            )
        )
        LaunchedEffect(Unit) {
            dataStore.preload() // preload the data store to avoid a delay when the first preference is read...
        }

        Window(
            title = "Compose Preferences Demo",
            onCloseRequest = ::exitApplication,
            state = rememberWindowState(
                position = WindowPosition(Alignment.Center),
                width = 800.dp,
                height = 600.dp
            )
        ) {
            MaterialTheme {
                DemoScaffold { modifier, showInfo ->
                    DemoContent(modifier, showInfo, prefs1, prefs2, dataStore)
                }
            }
        }
    }
}
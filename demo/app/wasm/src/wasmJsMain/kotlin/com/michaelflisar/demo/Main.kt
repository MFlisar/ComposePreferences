package com.michaelflisar.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.michaelflisar.demo.preferences.DemoDataStore
import com.michaelflisar.demo.preferences.DemoPrefs1
import com.michaelflisar.demo.preferences.DemoPrefs2
import com.michaelflisar.kotpreferences.storage.keyvalue.LocalStorageKeyValueStorage

val storage1 = LocalStorageKeyValueStorage.create(key = "demo_prefs1")
val storage2 = LocalStorageKeyValueStorage.create(key = "demo_prefs2")

@OptIn(ExperimentalComposeUiApi::class)
suspend fun main() {

    val prefs1 = DemoPrefs1(storage1)
    val prefs2 = DemoPrefs2(storage2)
    val dataStore = DemoDataStore() // only stored data in memory... good enough for demo purposes

    ComposeViewport(
        // mit container id geht es nicht --> wäre aber gut, dann würde ein Loader angezeigt werden, aktuell wird der nicht angezeigt...
        // viewportContainerId = wasmSetup.canvasElementId
    ) {

        DemoApp(prefs1, prefs2, dataStore)
    }
}
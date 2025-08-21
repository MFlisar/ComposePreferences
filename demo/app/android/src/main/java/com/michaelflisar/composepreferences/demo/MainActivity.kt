package com.michaelflisar.composepreferences.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.michaelflisar.composepreferences.demo.preferences.DemoPrefs1
import com.michaelflisar.composepreferences.demo.preferences.DemoPrefs2
import com.michaelflisar.democomposables.DemoScaffold
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create
import okio.Path.Companion.toOkioPath

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current

            val prefs1 = DemoPrefs1(DataStoreStorage.create(name = "demo_prefs1"))
            val prefs2 = DemoPrefs2(DataStoreStorage.create(name = "demo_prefs2"))
            val dataStore = DemoDataStore(
                PreferenceDataStoreFactory.createWithPath(
                    produceFile = {
                        context.filesDir.resolve("demo_data_store.preferences_pb").toOkioPath()
                    }
                )
            )
            LaunchedEffect(Unit) {
                dataStore.preload() // preload the data store to avoid a delay when the first preference is read...
            }

            MaterialTheme {
                DemoScaffold(
                    appName = stringResource(R.string.app_name)
                ) { modifier, showInfo ->
                    DemoContent(modifier, showInfo, prefs1, prefs2, dataStore)
                }
            }
        }
    }
}

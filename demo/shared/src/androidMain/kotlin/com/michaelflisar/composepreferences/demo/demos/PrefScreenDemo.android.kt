package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.demo.DispatcherIO
import com.michaelflisar.composepreferences.demo.demos.custom.AppItem
import com.michaelflisar.composepreferences.demo.demos.custom.SelectAppPreference
import com.michaelflisar.composepreferences.demo.preferences.DemoDataStore
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
actual fun PreferenceScope.PreferenceAppSelector(
    dataStore: DemoDataStore
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val app = dataStore.getString("app", "").collectAsState(initial = "")
    val appItem = remember { mutableStateOf<AppItem?>(null) }

    LaunchedEffect(app.value) {
        withContext(DispatcherIO) {
            appItem.value = SelectAppPreference.loadSingleApp(context, app.value)
        }
    }
    val item = appItem.value
    if (item != null) {
        SelectAppPreference(
            value = item,
            onValueChange = {
                scope.launch(DispatcherIO) {
                    dataStore.update("app", it.packageName)
                }
            },
            title = "App",
            subtitle = "Select a installed app",
            icon = { Icon(Icons.Default.Apps, null) }
        )
    }
}
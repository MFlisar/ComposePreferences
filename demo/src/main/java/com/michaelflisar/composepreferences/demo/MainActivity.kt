package com.michaelflisar.composepreferences.demo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.michaelflisar.composedemobaseactivity.DemoBaseActivity
import com.michaelflisar.composedemobaseactivity.classes.DemoTheme
import com.michaelflisar.composedemobaseactivity.classes.listSaverKeepEntryStateList
import com.michaelflisar.composedemobaseactivity.composables.DemoAppThemeRegion
import com.michaelflisar.composedemobaseactivity.composables.DemoCollapsibleRegion
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.classes.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.demo.classes.DemoDataStore
import com.michaelflisar.composepreferences.demo.classes.DemoPrefs
import com.michaelflisar.composepreferences.demo.classes.LocalDataStore
import com.michaelflisar.composepreferences.demo.composables.MyInfoLine
import com.michaelflisar.composepreferences.demo.demos.PrefScreenCustomDemo
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemo
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemoFilter
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemoKotPreferences1
import com.michaelflisar.composepreferences.kotpreferences.asPreferenceData
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.number.PreferenceNumber
import java.text.NumberFormat
import java.util.Locale

class MainActivity : DemoBaseActivity() {

    @Composable
    override fun Content(modifier: Modifier, theme: DemoTheme, dynamicTheme: Boolean) {

        // not beautiful but a easy solution good enough for demo purposes
        val context = LocalContext.current
        CompositionLocalProvider(
            LocalDataStore provides DemoDataStore(context)
        ) {
            // preload data store
            val dataStore = LocalDataStore.current
            LaunchedEffect(Unit) {
                dataStore.preload()
            }

            val page = rememberSaveable {
                mutableIntStateOf(0)
            }

            val expandedRootRegions = rememberSaveable(saver = listSaverKeepEntryStateList()) {
                mutableStateListOf(0, 2)
            }

            BackHandler(page.intValue != 0) {
                page.intValue = 0
            }

            Content(
                modifier = Modifier,
                page,
                theme,
                dynamicTheme,
                expandedRootRegions
            )
        }
    }

}

// ----------------
// UI - Content
// ----------------

@Composable
private fun Content(
    modifier: Modifier,
    page: MutableState<Int>,
    theme: DemoTheme,
    dynamicTheme: Boolean,
    expandedRootRegions: SnapshotStateList<Int>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (page.value) {
            0 -> Root(
                page, theme, dynamicTheme, expandedRootRegions,
                listOf("Demo", "Demo KotPreference", "Demo Custom", "Demo Filter")
            )

            1 -> PrefScreenDemo()
            2 -> PrefScreenDemoKotPreferences1()
            3 -> PrefScreenCustomDemo()
            4 -> PrefScreenDemoFilter()
        }
    }
}


@Composable
private fun Root(
    page: MutableState<Int>,
    theme: DemoTheme,
    dynamicTheme: Boolean,
    expandedRootRegions: SnapshotStateList<Int>,
    buttons: List<String>
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // App Theme Settings
        DemoAppThemeRegion(theme, dynamicTheme, id = 0, expandedIds = expandedRootRegions)

        // --------------------
        // App Settings for demos
        // --------------------

        DemoCollapsibleRegion(
            "Preference Settings",
            "Those settings are used for all preferences in this demo app!",
            id = 1,
            expandedIds = expandedRootRegions
        ) {

            val settingsState = DemoPrefs.preferenceSettings()
            val itemStyle = PreferenceStyleDefaults.item(
                shape = MaterialTheme.shapes.small
            )
            val settings by remember(settingsState) {
                derivedStateOf {
                    settingsState.copy(
                        // this shape looks better if settings are not used on full screen width
                        itemStyle = itemStyle,
                        // we disable the animation here
                        animationSpec = null
                    )
                }

            }

            PreferenceScreen(
                scrollable = false,
                settings = settings
            ) {
                PreferenceNumber(
                    data = DemoPrefs.disabledStateAlpha.asPreferenceData(),
                    title = { Text("Alpha") },
                    subtitle = { Text("Alpha value for disabled preferences") },
                    icon = { Icon(Icons.Default.Settings, null) },
                    min = 0f,
                    max = 1f,
                    stepSize = 0.1f,
                    formatter = {
                        NumberFormat.getInstance(Locale.ENGLISH).apply {
                            maximumFractionDigits = 1
                            minimumFractionDigits = 1
                        }.format(it)
                    }
                )
                PreferenceBool(
                    style = PreferenceBool.Style.Switch,
                    data = DemoPrefs.disabledStateGrayscale.asPreferenceData(),
                    title = { Text("Grayscale") },
                    subtitle = { Text("Grayscale out disabled preferences?") },
                    icon = { Icon(Icons.Default.Settings, null) }
                )
                PreferenceBool(
                    style = PreferenceBool.Style.Switch,
                    data = DemoPrefs.toggleBooleanOnItemClick.asPreferenceData(),
                    title = { Text("Bool Behaviour") },
                    subtitle = { Text("Toggle boolean preferences on item click (or on checkbox/switch click only)?") },
                    icon = { Icon(Icons.Default.Settings, null) }
                )
                PreferenceNumber(
                    data = DemoPrefs.maxLinesValue.asPreferenceData(),
                    title = { Text("Lines") },
                    subtitle = { Text("Maximum number of lines for all preferences that show some text inside their content area.") },
                    icon = { Icon(Icons.Default.Settings, null) },
                    min = 0,
                    max = 5,
                    stepSize = 1
                )
                PreferenceBool(
                    style = PreferenceBool.Style.Switch,
                    data = DemoPrefs.showSubScreenEndIndicator.asPreferenceData(),
                    title = { Text("Sub Screen Indicator") },
                    subtitle = { Text("Show an arrow on the right for sub screens?") },
                    icon = { Icon(Icons.Default.Settings, null) }
                )
                PreferenceBool(
                    style = PreferenceBool.Style.Switch,
                    data = DemoPrefs.forceNoIconInset.asPreferenceData(),
                    title = { Text("No Icon Inset") },
                    subtitle = { Text("Force an inset for preferences without an icon, so that all preferences align beautifully on the left?") },
                    icon = { Icon(Icons.Default.Settings, null) }
                )
            }
        }

        // --------------------
        // Demos
        // --------------------

        DemoCollapsibleRegion("Demos", id = 2, expandedIds = expandedRootRegions) {
            MyInfoLine(
                label = "INFORMATION",
                info = "Only the first demo showcases all available preferences - the other demos just showcase the basic usage because everything else just works the same!",
                showEqualSign = false
            )
            buttons.forEachIndexed { index, s ->
                OutlinedButton(
                    onClick = {
                        page.value = index + 1
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(s)
                }
            }
        }

    }
}
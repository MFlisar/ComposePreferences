package com.michaelflisar.composepreferences.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.michaelflisar.composepreferences.core.PreferenceScreen
import com.michaelflisar.composepreferences.core.classes.PreferenceStyleDefaults
import com.michaelflisar.composepreferences.demo.classes.AppPrefs
import com.michaelflisar.composepreferences.demo.classes.DemoDataStore
import com.michaelflisar.composepreferences.demo.classes.DemoTheme
import com.michaelflisar.composepreferences.demo.classes.LocalDataStore
import com.michaelflisar.composepreferences.demo.composables.MyCollapsibleRegion
import com.michaelflisar.composepreferences.demo.composables.MyInfoLine
import com.michaelflisar.composepreferences.demo.composables.SegmentedButtons
import com.michaelflisar.composepreferences.demo.demos.PrefScreenCustomDemo
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemo
import com.michaelflisar.composepreferences.demo.demos.PrefScreenDemoKotPreferences1
import com.michaelflisar.composepreferences.demo.theme.AppTheme
import com.michaelflisar.composepreferences.kotpreferences.asPreferenceData
import com.michaelflisar.composepreferences.screen.bool.PreferenceBool
import com.michaelflisar.composepreferences.screen.number.PreferenceNumber
import com.michaelflisar.kotpreferences.compose.collectAsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class DemoActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

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

                // collectAsState comes from preference module and allows us to simply use MaterialPreferences with compose
                val stateTheme = AppPrefs.theme.collectAsState()
                val stateDynamicTheme = AppPrefs.dynamicTheme.collectAsState()
                val theme = stateTheme.value
                val dynamicTheme = stateDynamicTheme.value
                if (theme == null || dynamicTheme == null)
                    return@CompositionLocalProvider

                val page = rememberSaveable {
                    mutableIntStateOf(0)
                }

                val expandedRootRegions = rememberSaveable(saver = listSaver(
                    save = { it.toList() },
                    restore = { it.toMutableStateList() }
                )) {
                    mutableStateListOf(0, 2)
                }


                BackHandler(page.intValue != 0) {
                    page.intValue = 0
                }

                AppTheme(
                    darkTheme = theme.isDark(),
                    dynamicColor = dynamicTheme
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column {
                            TopAppBar(
                                title = { Text(stringResource(R.string.app_name)) },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                            Content(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                page,
                                theme,
                                dynamicTheme,
                                expandedRootRegions
                            )
                        }
                    }
                }
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
                    listOf("Demo", "Demo KotPreference", "Demo Custom")
                )

                1 -> PrefScreenDemo()
                2 -> PrefScreenDemoKotPreferences1()
                3 -> PrefScreenCustomDemo()
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
        val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // --------------------
            // Infos about current state
            // --------------------

            MyCollapsibleRegion("App Theme", expandedId = 0, expanded = expandedRootRegions) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Theme", modifier = Modifier.weight(1f))
                    SegmentedButtons(
                        items = DemoTheme.values().map { it.name },
                        selectedIndex = theme.ordinal
                    ) {
                        scope.launch(Dispatchers.IO) {
                            AppPrefs.theme.update(DemoTheme.values()[it])
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Dynamic Theme", modifier = Modifier.weight(1f))
                    SegmentedButtons(
                        items = listOf("Yes", "No"),
                        selectedIndex = if (dynamicTheme) 0 else 1
                    ) {
                        scope.launch(Dispatchers.IO) {
                            AppPrefs.dynamicTheme.update(it == 0)
                        }
                    }
                }
            }

            // --------------------
            // App Settings for demos
            // --------------------

            MyCollapsibleRegion("Preference Settings", "Those settings are used for all preferences in this demo app!", expandedId = 1, expanded = expandedRootRegions) {

                val settingsState = AppPrefs.preferenceSettings()
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
                        data = AppPrefs.disabledStateAlpha.asPreferenceData(),
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
                        data = AppPrefs.disabledStateGrayscale.asPreferenceData(),
                        title = { Text("Grayscale") },
                        subtitle = { Text("Grayscale out disabled preferences?") },
                        icon = { Icon(Icons.Default.Settings, null) }
                    )
                    PreferenceBool(
                        style = PreferenceBool.Style.Switch,
                        data = AppPrefs.toggleBooleanOnItemClick.asPreferenceData(),
                        title = { Text("Bool Behaviour") },
                        subtitle = { Text("Toggle boolean preferences on item click (or on checkbox/switch click only)?") },
                        icon = { Icon(Icons.Default.Settings, null) }
                    )
                    PreferenceNumber(
                        data = AppPrefs.maxLinesValue.asPreferenceData(),
                        title = { Text("Lines") },
                        subtitle = { Text("Maximum number of lines for all preferences that show some text inside their content area.") },
                        icon = { Icon(Icons.Default.Settings, null) },
                        min = 0,
                        max = 5,
                        stepSize = 1
                    )
                    PreferenceBool(
                        style = PreferenceBool.Style.Switch,
                        data = AppPrefs.showSubScreenEndIndicator.asPreferenceData(),
                        title = { Text("Sub Screen Indicator") },
                        subtitle = { Text("Show an arrow on the right for sub screens?") },
                        icon = { Icon(Icons.Default.Settings, null) }
                    )
                    PreferenceBool(
                        style = PreferenceBool.Style.Switch,
                        data = AppPrefs.forceNoIconInset.asPreferenceData(),
                        title = { Text("No Icon Inset") },
                        subtitle = { Text("Force an inset for preferences without an icon, so that all preferences align beautifully on the left?") },
                        icon = { Icon(Icons.Default.Settings, null) }
                    )
                }
            }

            // --------------------
            // Demos
            // --------------------

            MyCollapsibleRegion("Demos", expandedId = 2, expanded = expandedRootRegions) {
                MyInfoLine(label = "INFORMATION", info = "Only the first demo showcases all available preferences - the other demos just showcase the basic usage because everything else just works the same!", showEqualSign = false)
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
}
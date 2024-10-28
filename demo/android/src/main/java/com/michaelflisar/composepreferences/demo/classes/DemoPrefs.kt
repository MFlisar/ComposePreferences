package com.michaelflisar.composepreferences.demo.classes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.michaelflisar.composepreferences.core.classes.PreferenceSettings
import com.michaelflisar.composepreferences.core.classes.PreferenceSettingsDefaults
import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.enumPref
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create
import kotlinx.coroutines.flow.combine

object DemoPrefs : SettingsModel(DataStoreStorage.create(name = "demo1_prefs")) {

    // PreferenceSettings
    val style by enumPref(DemoStyle.Default)
    val disabledStateAlpha by floatPref(.4f)
    val disabledStateGrayscale by boolPref(false)
    val toggleBooleanOnItemClick by boolPref(false)
    val maxLinesValue by intPref(2)
    val showSubScreenEndIndicator by boolPref(true)
    val forceNoIconInset by boolPref(false)

    // helper functions that converts all settings above into one flow and then converts this flow into a state
    // => this state will change whenever any setting in the group will change and this is what we want here...
    @Composable
    fun preferenceSettings(): PreferenceSettings {
        val settings = listOf(
            disabledStateAlpha,
            disabledStateGrayscale,
            toggleBooleanOnItemClick,
            maxLinesValue,
            showSubScreenEndIndicator,
            forceNoIconInset
        )
        val data =
            combine(settings.map { it.flow }) { it.toList() }.collectAsState(initial = emptyList())

        return if (data.value.isEmpty()) {
            PreferenceSettingsDefaults.settings()
        } else {
            val data = data.value
            PreferenceSettingsDefaults.settings(
                disabledStateAlpha = data[0] as Float,
                disabledStateGrayscale = data[1] as Boolean,
                toggleBooleanOnItemClick = data[2] as Boolean,
                maxLinesValue = data[3] as Int,
                subScreenEndIndicator = if (data[4] as Boolean) {
                    @Composable {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                } else null,
                forceNoIconInset = data[5] as Boolean,
            )
        }


        /*
        return SettingsGroup(
            disabledStateAlpha,
            disabledStateGrayscale,
            toggleBooleanOnItemClick,
            maxLinesValue,
            showSubScreenEndIndicator,
            forceNoIconInset,
        ) {
            PreferenceSettingsDefaults.settings(
                disabledStateAlpha = it[0] as Float,
                disabledStateGrayscale = it[1] as Boolean,
                toggleBooleanOnItemClick = it[2] as Boolean,
                maxLinesValue = it[3] as Int,
                subScreenEndIndicator = if (it[4] as Boolean) {
                    @Composable {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                } else null,
                forceNoIconInset = it[5] as Boolean,
            )
        }.flow.collectAsState(initial = null)*/
    }

}
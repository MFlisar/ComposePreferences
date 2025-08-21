package com.michaelflisar.composepreferences.demo.demos

import androidx.compose.runtime.Composable
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.composepreferences.demo.DemoDataStore

@Composable
actual fun PreferenceScope.PreferenceAppSelector(dataStore: DemoDataStore) {
    PreferenceInfo(title = "This is only available inside the android demo!")
}
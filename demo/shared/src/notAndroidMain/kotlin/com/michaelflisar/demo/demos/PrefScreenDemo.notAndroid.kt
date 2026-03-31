package com.michaelflisar.demo.demos

import androidx.compose.runtime.Composable
import com.michaelflisar.composepreferences.core.PreferenceInfo
import com.michaelflisar.composepreferences.core.scopes.PreferenceScope
import com.michaelflisar.demo.interfaces.IDemoDataStore

@Composable
actual fun PreferenceScope.PreferenceAppSelector(dataStore: IDemoDataStore) {
    PreferenceInfo(title = "This is only available inside the android demo!")
}
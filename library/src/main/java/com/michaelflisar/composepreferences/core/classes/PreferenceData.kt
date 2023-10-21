package com.michaelflisar.composepreferences.core.classes

import androidx.compose.runtime.MutableState

/*
 * helper class to be able to plug in KotPreferences separately as an optional module
 * (or any other custom data class of course, if the user has one)
 */

data class PreferenceData<T>(
    val value: T,
    val onValueChange: (data: T) -> Unit = {}
) {
    companion object
}

fun <T> MutableState<T>.asPreferenceData(): PreferenceData<T> {
    return PreferenceData(
        value
    ) { value = it }
}

//@Composable
//fun <T> PreferenceData(setting: StorageSetting<T>): PreferenceData<T> {
//    val scope = rememberCoroutineScope()
//    val state = setting.collectSetting()
//    return PreferenceData(state.value) {
//        scope.launch(Dispatchers.IO) {
//            setting.update(it)
//        }
//    }
//}
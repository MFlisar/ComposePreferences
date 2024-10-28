package com.michaelflisar.composepreferences.demo.classes

import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create

object Demo2Prefs : SettingsModel(DataStoreStorage.create(name = "demo2_prefs")) {

    // demo preferences
    val stringValue by stringPref("some string")
    val intValue by intPref(123)
    val boolValue by boolPref(true)

    // master/node example
    val master1 by boolPref(true)
    val node1a by intPref(123)
    val node1b by boolPref(true)
    val node1c by stringPref("Sub of master 1...")
    val master2 by boolPref(true)
    val node2a by intPref(456)
    val node2b by boolPref(false)
    val node2c by stringPref("Sub of master 2...")
}
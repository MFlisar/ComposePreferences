package com.michaelflisar.composepreferences.core.classes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.michaelflisar.composepreferences.core.AtomicInt

class PreferenceState internal constructor(
    internal val items: MutableState<List<Item>> = mutableStateOf(emptyList()),
    val openedGroups: SnapshotStateList<Int> = mutableStateListOf()
) {
    class Item(
        val id: Int,
        val level: Int,
        val type: PreferenceType,
        val visible: Boolean
    )

    fun countCurrentLevel(includeSections: Boolean = true, includeGroups: Boolean = true) =
        items.value
            .filter { it.level == currentLevel }
            .filter { includeSections || it.type != PreferenceType.Section }
            .filter { includeGroups || it.type != PreferenceType.Group }
            .size

    fun countVisible(includeSections: Boolean = true, includeGroups: Boolean = true) =
        items.value
            .filter { it.visible }
            .filter { includeSections || it.type != PreferenceType.Section }
            .filter { includeGroups || it.type != PreferenceType.Group }
            .size

    fun countAll(includeSections: Boolean = true, includeGroups: Boolean = true) =
        items.value
            .filter { it.type == PreferenceType.Item }
            .filter { includeSections || it.type != PreferenceType.Section }
            .filter { includeGroups || it.type != PreferenceType.Group }
            .size

    val currentLevel: Int
        get() = openedGroups.size

    private val id = AtomicInt(1)
    internal fun getNextID() = id.getAndIncrement()

    fun popLast() {
        if (openedGroups.size > 0) {
            openedGroups.removeLast()
        }
    }

    fun popAll() {
        if (openedGroups.size > 0) {
            openedGroups.clear()
        }
    }
}

@Composable
fun rememberPreferenceState() = PreferenceState(
    items = remember { mutableStateOf(emptyList()) },
    openedGroups = rememberOpenedGroups()
)

@Composable
private fun rememberOpenedGroups(): SnapshotStateList<Int> {
    return rememberSaveable(saver = listSaver(
        save = { it.toList() },
        restore = { it.toMutableStateList() }
    )) {
        mutableStateListOf()
    }
}
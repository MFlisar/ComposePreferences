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
import com.michaelflisar.parcelize.Parcelable
import com.michaelflisar.parcelize.Parcelize
import kotlinx.atomicfu.atomic

class PreferenceState internal constructor(
    private val items: MutableState<List<Item>> = mutableStateOf(emptyList()),
    private val openedGroups: SnapshotStateList<Group> = mutableStateListOf(),
) {
    val opened: List<Group>
        get() = openedGroups.toList()

    class Item(
        val id: Int,
        val level: Int,
        val type: PreferenceType,
        val visible: Boolean,
    )

    @Parcelize
    class Group(
        val id: Int,
        val title: String,
    ) : Parcelable

    fun countCurrentLevel(includeSections: Boolean = true, includeGroups: Boolean = true) =
        items.value
            .filter { it.level == currentLevel }
            .filter { includeSections || it.type !is PreferenceType.Section }
            .filter { includeGroups || it.type != PreferenceType.Group }
            .size

    fun countVisible(includeSections: Boolean = true, includeGroups: Boolean = true) =
        items.value
            .filter { it.visible }
            .filter { includeSections || it.type !is PreferenceType.Section }
            .filter { includeGroups || it.type != PreferenceType.Group }
            .size

    fun countAll(includeSections: Boolean = true, includeGroups: Boolean = true) =
        items.value
            .filter { it.type == PreferenceType.Item }
            .filter { includeSections || it.type !is PreferenceType.Section }
            .filter { includeGroups || it.type != PreferenceType.Group }
            .size

    val currentLevel: Int
        get() = openedGroups.size

    private val id = atomic(1)
    internal fun getNextID() = id.getAndIncrement()

    fun push(group: Group) {
        openedGroups.add(group)
    }

    fun popLast() {
        //println("BACK - popLast")
        if (openedGroups.size > 0) {
            openedGroups.removeAt(openedGroups.lastIndex)
        }
    }

    fun popAll() {
        if (openedGroups.isNotEmpty()) {
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
private fun rememberOpenedGroups(): SnapshotStateList<PreferenceState.Group> {
    return rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { it.toMutableStateList() }
        )) {
        mutableStateListOf()
    }
}
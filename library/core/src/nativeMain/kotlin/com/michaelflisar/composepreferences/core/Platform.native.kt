package com.michaelflisar.composepreferences.core

import androidx.compose.runtime.Composable
import kotlin.concurrent.AtomicInt

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // empty
}

actual class AtomicInt actual constructor(initialValue:Int){
    private val atom = AtomicInt(initialValue)

    actual fun get(): Int = atom.value

    actual fun set(newValue: Int) {
        atom.value = newValue
    }

    actual fun getAndIncrement(): Int = atom.getAndAdd(1)

    actual fun incrementAndGet(): Int = atom.addAndGet(1)

    actual fun decrementAndGet(): Int = atom.addAndGet(-1)

    actual fun addAndGet(delta: Int): Int = atom.addAndGet(delta)

    actual fun compareAndSet(expected: Int, new: Int): Boolean = atom.compareAndSet(expected, new)

}
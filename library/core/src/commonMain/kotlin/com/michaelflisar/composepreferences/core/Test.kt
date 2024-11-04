package com.michaelflisar.composepreferences.core

internal object Test {

    // TODO: HACK to avoid java.lang.NoSuchMethodError: No virtual method removeLast()Ljava/lang/Object; in class Landroidx/compose/runtime/snapshots/SnapshotStateList; or its super classes
    // issue only happens on android!
    const val useRemoveLastOrNull = true

    // TODO: Animations + ScrollState restoration do not work well together
    // on back does always reset the scrollstate because items are resized
    // PROBLEM: AnimatedPreference does resize all items so whenever the state is restored the scrollstate gets resetted by the column
    //          because the content is initially of height 0...
    // IDEA: use different nested screens and show/hide them and let them animate the items
    //      => ID management must be done for invisible screens so those screens would need to stay
    //      in the composable tree just like the preferences do it now...
    // disabling animation solves the issue => this is currently done!
    const val useAnimation = true
    const val useScrollStateRestoration = false
}
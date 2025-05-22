package com.michaelflisar.composepreferences.core

internal object ExperimentalSettings {

    // TODO: Animations + ScrollState restoration do not work well together
    // on back does always reset the scrollstate because items are resized
    // PROBLEM: AnimatedPreference does resize all items so whenever the state is restored the scrollstate gets resetted by the column
    //          because the content is initially of height 0...
    // IDEA: use different nested screens and show/hide them and let them animate the items
    //      => ID management must be done for invisible screens so those screens would need to stay
    //      in the composable tree just like the preferences do it now...
    // CURRENT STATE: SOLVED!
    var USE_ANIMATION = true
    //var USE_SCROLLSTATE_RESTORATON = true

    /*
     * if enables, on android 12 preferences are invisibile in some cases, e.g. with modern style:
     * - open demo
     * - click info demos
     * - go back
     * ...
     */
    const val USE_ALPHA_AND_TRANSLATE_ON_FIRST_SHOW = false
}
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
    var useAnimation = true
    var useScrollStateRestoration = true

    /*
     * if enables, on android 12 preferences are invisibile in some cases, e.g. with modern style:
     * - open demo
     * - click info demos
     * - go back
     * ...
     */
    const val USE_ALPHA_AND_TRANSLATE_ON_FIRST_SHOW = false

    /*
     * Enable animation for preferences but disabled scrollstate restoration when moving up/down in the hierarchy
     *
     * this is the default setting
     */
    fun useAnimation() {
        useAnimation = true
        useScrollStateRestoration = false
    }

    /*
     * Enable scrollstate restoration when moving up/down in the hierarchy but disable animations for preferences
     */
    fun useScrollStateRestoration() {
        useAnimation = false
        useScrollStateRestoration = true
    }
}
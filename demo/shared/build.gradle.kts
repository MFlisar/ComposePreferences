import com.michaelflisar.kmpdevtools.Targets
import com.michaelflisar.kmpdevtools.BuildFileUtil
import com.michaelflisar.kmpdevtools.core.Platform
import com.michaelflisar.kmpdevtools.configs.*
import com.michaelflisar.kmpdevtools.setupDependencies
import com.michaelflisar.kmpdevtools.setupBuildKonfig

plugins {
    // kmp + app/library
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    // org.jetbrains.kotlin
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.parcelize)
    // org.jetbrains.compose
    alias(libs.plugins.jetbrains.compose)
    // docs, publishing, validation
    // --
    // build tools
    alias(mflisar.plugins.kmpdevtools.buildplugin)
    alias(libs.plugins.buildkonfig)
    // others
    // ...
}

// ------------------------
// Setup
// ------------------------

val module = LibraryModuleConfig.readManual(project)

// targets
val buildTargets = Targets(
    // mobile
    android = true,
    iOS = true,
    // desktop
    windows = true,
    macOS = false, // because of compose unstyled dialogs
    // web
    wasm = true
)

val androidConfig = AndroidLibraryConfig.createFromPath(
    libraryModuleConfig = module,
    compileSdk = app.versions.compileSdk,
    minSdk = app.versions.minSdk,
    enableAndroidResources = true
)

// ------------------------
// Kotlin
// ------------------------

buildkonfig {
    setupBuildKonfig(module.appConfig)
}

kotlin {

    //-------------
    // Targets
    //-------------

    buildTargets.setupTargetsLibrary(module)
    android {
        buildTargets.setupTargetsAndroidLibrary(module, androidConfig, this)
    }

    // -------
    // Sources
    // -------

    sourceSets {

        // ---------------------
        // custom source sets
        // ---------------------

        val iosMain by creating { dependsOn(commonMain.get()) }
        val notAndroidMain by creating { dependsOn(commonMain.get()) }
        val notWasmJsMain by creating { dependsOn(commonMain.get()) }

        setupDependencies(module, buildTargets, sourceSets) {

            Platform.IOS addSourceSet iosMain

            notAndroidMain supportedBy !Platform.ANDROID
            notWasmJsMain supportedBy !Platform.WASM

        }

        // ---------------------
        // dependencies
        // ---------------------

        commonMain.dependencies {

            // Kotlin
            api(libs.jetbrains.kotlinx.coroutines.core)

            // Compose
            api(libs.jetbrains.compose.material3)
            api(libs.jetbrains.compose.material.icons.core)
            api(libs.jetbrains.compose.material.icons.extended)
            api(libs.jetbrains.compose.ui.backhandler)

            // ------------------------
            // Libraries
            // ------------------------

            api(project(":composepreferences:core"))
            api(project(":composepreferences:modules:screen:bool"))
            api(project(":composepreferences:modules:screen:button"))
            api(project(":composepreferences:modules:screen:input"))
            api(project(":composepreferences:modules:screen:color"))
            api(project(":composepreferences:modules:screen:date"))
            api(project(":composepreferences:modules:screen:time"))
            api(project(":composepreferences:modules:screen:list"))
            api(project(":composepreferences:modules:screen:number"))
            api(project(":composepreferences:modules:kotpreferences"))

            // KotPreferences
            api(mflisar.kotpreferences.core)
            api(mflisar.kotpreferences.extension.compose)

            // Dialogs
            api(mflisar.composedialogs.core)
            api(mflisar.composedialogs.dialog.list)

            // demo ui composables
            api(mflisar.democomposables)
        }

        notWasmJsMain.dependencies {

            api(libs.androidx.datastore.preferences)

            api(mflisar.kotpreferences.storage.datastore)
        }

        androidMain.dependencies {

            api(deps.drawablepainter)

            api(libs.jetbrains.compose.ui.tooling)
            api(libs.jetbrains.compose.ui.tooling.preview)
        }

        wasmJsMain.dependencies {
            api(mflisar.kotpreferences.storage.keyvalue)
        }
    }
}
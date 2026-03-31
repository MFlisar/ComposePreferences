import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import com.michaelflisar.kmpdevtools.Targets
import com.michaelflisar.kmpdevtools.configs.library.AndroidLibraryConfig
import com.michaelflisar.kmpdevtools.core.Platform
import com.michaelflisar.kmpdevtools.core.configs.AppConfig
import com.michaelflisar.kmpdevtools.core.configs.Config
import com.michaelflisar.kmpdevtools.core.configs.LibraryConfig
import com.michaelflisar.kmpdevtools.setupDependencies

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
    alias(deps.plugins.kmpdevtools.buildplugin)
    alias(libs.plugins.buildkonfig)
    // others
    // ...
}

// ------------------------
// Setup
// ------------------------

val config = Config.read(rootProject)
val libraryConfig = LibraryConfig.read(rootProject)
val appConfig = AppConfig.read(rootProject)

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

val androidConfig = AndroidLibraryConfig.createManualNamespace(
    compileSdk = app.versions.compileSdk,
    minSdk = app.versions.minSdk,
    enableAndroidResources = true,
    namespaceAddon = "demo.shared"
)

// ------------------------
// Kotlin
// ------------------------

buildkonfig {
    packageName = appConfig.packageName
    exposeObjectWithName = "BuildKonfig"
    defaultConfigs {
        buildConfigField(Type.STRING, "versionName", appConfig.versionName)
        buildConfigField(Type.INT, "versionCode", appConfig.versionCode.toString())
        buildConfigField(Type.STRING, "packageName", appConfig.packageName)
        buildConfigField(Type.STRING, "appName", appConfig.name)
    }
}

kotlin {

    //-------------
    // Targets
    //-------------

    buildTargets.setupTargetsLibrary(project)
    android {
        buildTargets.setupTargetsAndroidLibrary(project, config, libraryConfig, androidConfig, this)
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

        setupDependencies(buildTargets, sourceSets) {

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
            api(deps.kotpreferences.core)
            api(deps.kotpreferences.extension.compose)

            // Dialogs
            api(deps.composedialogs.core)
            api(deps.composedialogs.dialog.list)

            // demo ui composables
            api(deps.kmp.democomposables)
        }

        notWasmJsMain.dependencies {

            api(libs.androidx.datastore.preferences)

            api(deps.kotpreferences.storage.datastore)
        }

        androidMain.dependencies {

            api(deps.drawablepainter)

            api(libs.jetbrains.compose.ui.tooling)
            api(libs.jetbrains.compose.ui.tooling.preview)
        }

        wasmJsMain.dependencies {
            api(deps.kotpreferences.storage.keyvalue)
        }
    }
}
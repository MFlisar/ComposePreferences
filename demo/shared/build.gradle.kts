import com.michaelflisar.kmplibrary.BuildFilePlugin
import com.michaelflisar.kmplibrary.setupDependencies
import com.michaelflisar.kmplibrary.Target
import com.michaelflisar.kmplibrary.Targets
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(deps.plugins.kmplibrary.buildplugin)
}

// get build logic plugin
val buildFilePlugin = project.plugins.getPlugin(BuildFilePlugin::class.java)

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

val androidNamespace = "com.michaelflisar.composedialogs.demo.shared"

// -------------------
// Setup
// -------------------

kotlin {

    //-------------
    // Targets
    //-------------

    buildFilePlugin.setupTargetsLibrary(buildTargets)

    // -------
    // Sources
    // -------

    sourceSets {

        // ---------------------
        // custom source sets
        // ---------------------

        val notAndroidMain by creating { dependsOn(commonMain.get()) }
        val notWasmJsMain by creating { dependsOn(commonMain.get()) }

        notAndroidMain.setupDependencies(sourceSets, buildTargets, listOf(Target.ANDROID), targetsNotSupported = true)
        notWasmJsMain.setupDependencies(sourceSets, buildTargets, listOf(Target.WASM), targetsNotSupported = true)

        // ---------------------
        // dependencies
        // ---------------------

        commonMain.dependencies {

            // Kotlin
            implementation(kotlinx.coroutines.core)

            // Compose
            implementation(libs.compose.material3)
            implementation(libs.compose.material.icons.core)
            implementation(libs.compose.material.icons.extended)
            implementation(libs.compose.ui.backhandler)

            // ------------------------
            // Libraries
            // ------------------------

            implementation(project(":composepreferences:core"))
            implementation(project(":composepreferences:modules:screen:bool"))
            implementation(project(":composepreferences:modules:screen:button"))
            implementation(project(":composepreferences:modules:screen:input"))
            implementation(project(":composepreferences:modules:screen:color"))
            implementation(project(":composepreferences:modules:screen:date"))
            implementation(project(":composepreferences:modules:screen:time"))
            implementation(project(":composepreferences:modules:screen:list"))
            implementation(project(":composepreferences:modules:screen:number"))
            implementation(project(":composepreferences:modules:kotpreferences"))

            // KotPreferences
            api(deps.kotpreferences.core)
            api(deps.kotpreferences.extension.compose)

            // Dialogs
            implementation(deps.composedialogs.core)
            implementation(deps.composedialogs.dialog.list)

            // demo ui composables
            api(deps.kmp.democomposables)
        }

        notWasmJsMain.dependencies {

            api(androidx.datastore.preferences)

            api(deps.kotpreferences.storage.datastore)
        }

        androidMain.dependencies {

            implementation(deps.drawablepainter)

            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.components.ui.tooling.preview)
        }

        wasmJsMain.dependencies {
            api(deps.kotpreferences.storage.keyvalue)
        }
    }
}

// -------------------
// Configurations
// -------------------

// android configuration
android {
    buildFilePlugin.setupAndroidLibrary(
        androidNamespace = androidNamespace,
        compileSdk = app.versions.compileSdk,
        minSdk = app.versions.minSdk,
        buildConfig = false
    )
}




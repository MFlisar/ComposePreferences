import com.michaelflisar.kmpgradletools.BuildFilePlugin
import com.michaelflisar.kmpgradletools.Target
import com.michaelflisar.kmpgradletools.Targets

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.maven.publish.plugin)
    alias(libs.plugins.binary.compatibility.validator)
    alias(deps.plugins.kmp.gradle.tools.gradle.plugin)
}

// get build file plugin
val buildFilePlugin = project.plugins.getPlugin(BuildFilePlugin::class.java)

// -------------------
// Informations
// -------------------

val androidNamespace = "com.michaelflisar.composepreferences.screen.time"

val buildTargets = Targets(
    // mobile
    android = true,
    iOS = true,
    // desktop
    windows = true,
    macOS = false, // macOS is not supported by compose dialogs currently
    // web
    wasm = true
)

// -------------------
// Setup
// -------------------

dependencies {
    coreLibraryDesugaring(libs.desugar)
}

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
        // custom shared sources
        // ---------------------

        // --
        // e.g.:
        // val nativeMain by creating { dependsOn(commonMain.get()) }

        // ---------------------
        // target sources
        // ---------------------

        // --
        // e.g.:
        // buildTargets.updateSourceSetDependencies(sourceSets) { groupMain, target ->
        //     when (target) {
        //         Target.ANDROID, Target.WINDOWS -> {
        //             groupMain.dependsOn(nativeMain)
        //         }
        //         Target.IOS, Target.MACOS, Target.WASM -> {
        //             // --
        //         }
        //         Target.LINUX,
        //         Target.JS -> {
        //             // not enabled
        //         }
        //     }
        // }

        // ---------------------
        // dependencies
        // ---------------------

        commonMain.dependencies {

            // Kotlin
            api(kotlinx.datetime)

            // Compose
            implementation(libs.compose.material3)

            val useLiveDependencies = providers.gradleProperty("useLiveDependencies").get().toBoolean()
            if (useLiveDependencies) {
                implementation(deps.composedialogs.core)
                implementation(deps.composedialogs.dialog.time)
            } else {
                implementation(project(":composedialogs:core"))
                implementation(project(":composedialogs:modules:time"))
            }

            implementation(project(":composepreferences:core"))
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.components.ui.tooling.preview)
        }

        jvmMain.dependencies {
            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.components.ui.tooling.preview)
        }
    }
}

// -------------------
// Configurations
// -------------------

// android configuration
android {

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    buildFilePlugin.setupAndroidLibrary(
        androidNamespace = androidNamespace,
        compileSdk = app.versions.compileSdk,
        minSdk = app.versions.minSdk,
        buildConfig = false
    )
}

// maven publish configuration
buildFilePlugin.setupMavenPublish()
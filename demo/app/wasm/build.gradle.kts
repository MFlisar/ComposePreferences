import com.michaelflisar.kmpdevtools.Targets
import com.michaelflisar.kmpdevtools.core.Target
import com.michaelflisar.kmpdevtools.configs.*

plugins {
    // kmp + app/library
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    // org.jetbrains.kotlin
    alias(libs.plugins.jetbrains.kotlin.compose)
    // org.jetbrains.compose
    alias(libs.plugins.jetbrains.compose)
    // docs, publishing, validation
    // --
    // build tools
    alias(mflisar.plugins.kmpdevtools.buildplugin)
    // others
    // --
}

// ------------------------
// Setup
// ------------------------

val module = AppModuleConfig.read(project)

val buildTargets = Targets(Target.WASM)

val wasmConfig = WasmAppConfig(
    moduleName = "demo",
    outputFileName = "demo.js"
)

// -------------------
// Kotlin
// -------------------

kotlin {

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    //-------------
    // Targets
    //-------------

    buildTargets.setupTargetsApp(module, wasmAppConfig = wasmConfig)

    // -------
    // Sources
    // -------

    sourceSets {

        // ---------------------
        // custom source sets
        // ---------------------

        // --

        // ---------------------
        // dependencies
        // ---------------------

        commonMain.dependencies {
            implementation(project(":demo:shared"))
        }
    }
}

// -------------------
// Configurations
// -------------------

// can be removed after upgrading kot preferences to 4.0.1
//configurations.named("wasmJsRuntimeClasspath") {
//    exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-io-core")
//}

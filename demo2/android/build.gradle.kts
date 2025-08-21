import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.compose)
}

android {

    namespace = "com.michaelflisar.composepreferences.demo"

    compileSdk = app.versions.compileSdk.get().toInt()

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = app.versions.minSdk.get().toInt()
        targetSdk = app.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    // eventually use local custom signing
    val debugKeyStore = providers.gradleProperty("debugKeyStore").orNull
    if (debugKeyStore != null) {
        signingConfigs {
            getByName("debug") {
                keyAlias = "androiddebugkey"
                keyPassword = "android"
                storeFile = File(debugKeyStore)
                storePassword = "android"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {

    // ------------------------
    // Kotlin
    // ------------------------

    implementation(kotlinx.datetime)

    // ------------------------
    // AndroidX
    // ------------------------

    // Compose

    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)
    implementation(deps.drawablepainter)

    implementation(androidx.activity.compose)
    implementation(androidx.datastore.preferences)

    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.components.ui.tooling.preview)

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
    implementation(deps.kotpreferences.core)
    implementation(deps.kotpreferences.storage.datastore)
    implementation(deps.kotpreferences.extension.compose)

    // Dialogs
    implementation(deps.composedialogs.core)
    implementation(deps.composedialogs.dialog.list)

    // ------------------------
    // Desugar
    // ------------------------

    coreLibraryDesugaring(libs.desugar)

    // ------------------------
    // Others
    // ------------------------

    implementation(deps.toolbox.ui)
    implementation(deps.toolbox.android.demo.app)

    //implementation(project(":demo:shared"))
}
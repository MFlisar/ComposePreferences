plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
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

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {

    // ------------------------
    // Kotlin
    // ------------------------

    implementation(libs.kotlin)

    implementation(libs.kotlinx.datetime)

    // ------------------------
    // AndroidX
    // ------------------------

    // Compose

    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.drawablepainter)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview.android)

    // ------------------------
    // Libraries
    // ------------------------

    implementation(project(":ComposePreferences:Core"))
    implementation(project(":ComposePreferences:Modules:Screen:Bool"))
    implementation(project(":ComposePreferences:Modules:Screen:Button"))
    implementation(project(":ComposePreferences:Modules:Screen:Input"))
    implementation(project(":ComposePreferences:Modules:Screen:Color"))
    implementation(project(":ComposePreferences:Modules:Screen:Date"))
    implementation(project(":ComposePreferences:Modules:Screen:Time"))
    implementation(project(":ComposePreferences:Modules:Screen:List"))
    implementation(project(":ComposePreferences:Modules:Screen:Number"))
    implementation(project(":ComposePreferences:Modules:KotPreferences"))

    // KotPreferences
    implementation(libs.kotpreferences.core)
    implementation(libs.kotpreferences.storage.datastore)
    implementation(libs.kotpreferences.extension.compose)

    // Dialogs
    implementation(libs.composedialogs.core)
    implementation(libs.composedialogs.dialog.list)

    // ------------------------
    // Desugar
    // ------------------------

    coreLibraryDesugaring(libs.desugar)

    // ------------------------
    // Others
    // ------------------------

    // a minimal library that provides some useful composables that I use inside demo activities
    implementation(libs.toolbox.core)
    implementation(libs.toolbox.ui)
    implementation(libs.toolbox.android.demo.app)
}
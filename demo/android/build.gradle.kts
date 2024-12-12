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

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
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
    implementation(deps.kotpreferences.core)
    implementation(deps.kotpreferences.storage.datastore)
    implementation(deps.kotpreferences.extension.compose)

    // Dialogs
    implementation(deps.composedialogs.core)
    implementation(deps.composedialogs.dialog.list)

    // ------------------------
    // Desugar
    // ------------------------

    coreLibraryDesugaring(deps.desugar)

    // ------------------------
    // Others
    // ------------------------

    // a minimal library that provides some useful composables that I use inside demo activities
    implementation(deps.toolbox.core)
    implementation(deps.toolbox.ui)
    implementation(deps.toolbox.android.demo.app)
}
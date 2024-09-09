plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    alias(libs.plugins.compose.compiler)
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

    composeOptions {
        kotlinCompilerExtensionVersion = compose.versions.compiler.get()
    }
}

dependencies {

    // ------------------------
    // Kotlin
    // ------------------------

    implementation(libs.kotlin)

    // ------------------------
    // AndroidX
    // ------------------------

    // Compose
    implementation(platform(compose.bom))
    implementation(compose.material3)
    implementation(compose.activity)
    implementation(compose.material.extendedicons)
    implementation(compose.drawablepainter)

    implementation(androidx.datastore.preferences)

    implementation(compose.ui.tooling)
    implementation(compose.ui.tooling.preview)

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
    // implementation(deps.kotpreferences.core) already included by the kotpreferences module!
    implementation(deps.kotpreferences.datastore)
    implementation(deps.kotpreferences.compose)

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
    implementation(deps.composedemobaseactivity)
}
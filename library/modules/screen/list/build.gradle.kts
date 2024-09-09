plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    alias(libs.plugins.compose.compiler)
}

android {

    namespace = "com.michaelflisar.composepreferences.screen.list"

    compileSdk = app.versions.compileSdk.get().toInt()

    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        minSdk = app.versions.minSdk.get().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            consumerProguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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
    // AndroidX / Google / Goolge
    // ------------------------

    // Compose BOM
    implementation(platform(compose.bom))
    implementation(compose.material3)
    implementation(compose.activity)
    implementation(compose.ui.tooling)
    implementation(compose.ui.tooling.preview)

    // ------------------------
    // Dialog
    // ------------------------

    val useLiveDependencies = providers.gradleProperty("useLiveDependencies").get().toBoolean()
    if (useLiveDependencies) {
        implementation(deps.composedialogs.core)
        implementation(deps.composedialogs.dialog.list)
    } else {
        implementation(project(":ComposeDialogs:Core"))
        implementation(project(":ComposeDialogs:Modules:List"))
    }

    // ------------------------
    // Libraries
    // ------------------------

    implementation(project(":ComposePreferences:Core"))
}

project.afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                artifactId = "screen-list"
                from(components["release"])
            }
        }
    }
}
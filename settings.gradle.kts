dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
    versionCatalogs {

        val kotlin = "1.9.23"
        val ksp = "1.9.23-1.0.20"
        val coroutines = "1.7.3"
        val gradle = "8.3.1"

        // TOML Files
        create("androidx") {
            from(files("gradle/androidx.versions.toml"))
        }
        create("deps") {
            from(files("gradle/dependencies.versions.toml"))
        }
        create("compose") {
            from(files("gradle/compose.versions.toml"))
        }

        // Rest
        create("tools") {
            version("kotlin", kotlin)
            version("gradle", gradle)
            version("ksp", ksp)
        }
        create("app") {
            version("compileSdk", "34")
            version("minSdk", "21")
            version("targetSdk", "34")
        }
        create("libs") {
            // Kotlin
            library("kotlin", "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin")
            library("kotlin.coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
            library("kotlin.reflect", "org.jetbrains.kotlin:kotlin-reflect:$kotlin")
        }
    }
}

// --------------
// App
// --------------

include(":ComposePreferences:Core")
project(":ComposePreferences:Core").projectDir = file("library/core")

include(":ComposePreferences:Modules:Screen:Bool")
project(":ComposePreferences:Modules:Screen:Bool").projectDir = file("library/modules/screen/bool")
include(":ComposePreferences:Modules:Screen:Input")
project(":ComposePreferences:Modules:Screen:Input").projectDir = file("library/modules/screen/input")
include(":ComposePreferences:Modules:Screen:Button")
project(":ComposePreferences:Modules:Screen:Button").projectDir = file("library/modules/screen/button")
include(":ComposePreferences:Modules:Screen:Color")
project(":ComposePreferences:Modules:Screen:Color").projectDir = file("library/modules/screen/color")
include(":ComposePreferences:Modules:Screen:Date")
project(":ComposePreferences:Modules:Screen:Date").projectDir = file("library/modules/screen/date")
include(":ComposePreferences:Modules:Screen:Time")
project(":ComposePreferences:Modules:Screen:Time").projectDir = file("library/modules/screen/time")
include(":ComposePreferences:Modules:Screen:List")
project(":ComposePreferences:Modules:Screen:List").projectDir = file("library/modules/screen/list")
include(":ComposePreferences:Modules:Screen:Number")
project(":ComposePreferences:Modules:Screen:Number").projectDir = file("library/modules/screen/number")

include(":ComposePreferences:Modules:KotPreferences")
project(":ComposePreferences:Modules:KotPreferences").projectDir = file("library/modules/kotpreferences")

include(":demo")
project(":demo").projectDir = file("demo")

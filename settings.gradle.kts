dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
    versionCatalogs {

        val kotlin = "1.9.10"
        val ksp = "1.9.10-1.0.13"
        val coroutines = "1.7.3"
        val gradle = "8.1.2"
        val maven = "2.0"

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
            version("maven", maven)
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
project(":ComposePreferences:Core").projectDir = file("library")

include(":ComposePreferences:Modules:Screen:Bool")
project(":ComposePreferences:Modules:Screen:Bool").projectDir = file("modules/screen/bool")
include(":ComposePreferences:Modules:Screen:Input")
project(":ComposePreferences:Modules:Screen:Input").projectDir = file("modules/screen/input")
include(":ComposePreferences:Modules:Screen:Button")
project(":ComposePreferences:Modules:Screen:Button").projectDir = file("modules/screen/button")
include(":ComposePreferences:Modules:Screen:Color")
project(":ComposePreferences:Modules:Screen:Color").projectDir = file("modules/screen/color")
include(":ComposePreferences:Modules:Screen:Date")
project(":ComposePreferences:Modules:Screen:Date").projectDir = file("modules/screen/date")
include(":ComposePreferences:Modules:Screen:Time")
project(":ComposePreferences:Modules:Screen:Time").projectDir = file("modules/screen/time")
include(":ComposePreferences:Modules:Screen:List")
project(":ComposePreferences:Modules:Screen:List").projectDir = file("modules/screen/list")
include(":ComposePreferences:Modules:Screen:Number")
project(":ComposePreferences:Modules:Screen:Number").projectDir = file("modules/screen/number")

include(":ComposePreferences:Modules:KotPreferences")
project(":ComposePreferences:Modules:KotPreferences").projectDir = file("modules/kotpreferences")

include(":demo")
project(":demo").projectDir = file("demo")
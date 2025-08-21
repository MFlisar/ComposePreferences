dependencyResolutionManagement {

    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }

    versionCatalogs {
        create("app") {
            from(files("gradle/app.versions.toml"))
        }
        create("androidx") {
            from(files("gradle/androidx.versions.toml"))
        }
        create("kotlinx") {
            from(files("gradle/kotlinx.versions.toml"))
        }
        create("deps") {
            from(files("gradle/deps.versions.toml"))
        }
    }
}

pluginManagement {

    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

// --------------
// Functions
// --------------

fun includeModule(path: String, name: String) {
    include(name)
    project(name).projectDir = file(path)
}

// --------------
// Library
// --------------

includeModule("library/core", ":composepreferences:core")
includeModule("library/modules/screen/info", ":composepreferences:modules:screen:info")
includeModule("library/modules/screen/bool", ":composepreferences:modules:screen:bool")
includeModule("library/modules/screen/input", ":composepreferences:modules:screen:input")
includeModule("library/modules/screen/button", ":composepreferences:modules:screen:button")
includeModule("library/modules/screen/color", ":composepreferences:modules:screen:color")
includeModule("library/modules/screen/date", ":composepreferences:modules:screen:date")
includeModule("library/modules/screen/time", ":composepreferences:modules:screen:time")
includeModule("library/modules/screen/list", ":composepreferences:modules:screen:list")
includeModule("library/modules/screen/number", ":composepreferences:modules:screen:number")

includeModule("library/modules/kotpreferences", ":composepreferences:modules:kotpreferences")

// --------------
// Demo
// --------------

include(":demo:shared")
include(":demo:app:windows")
include(":demo:app:android")

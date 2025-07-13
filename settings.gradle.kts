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
// Library
// --------------

include(":composepreferences:core")
project(":composepreferences:core").projectDir = file("library/core")

include(":composepreferences:modules:screen:bool")
project(":composepreferences:modules:screen:bool").projectDir = file("library/modules/screen/bool")
include(":composepreferences:modules:screen:input")
project(":composepreferences:modules:screen:input").projectDir = file("library/modules/screen/input")
include(":composepreferences:modules:screen:button")
project(":composepreferences:modules:screen:button").projectDir = file("library/modules/screen/button")
include(":composepreferences:modules:screen:color")
project(":composepreferences:modules:screen:color").projectDir = file("library/modules/screen/color")
include(":composepreferences:modules:screen:date")
project(":composepreferences:modules:screen:date").projectDir = file("library/modules/screen/date")
include(":composepreferences:modules:screen:time")
project(":composepreferences:modules:screen:time").projectDir = file("library/modules/screen/time")
include(":composepreferences:modules:screen:list")
project(":composepreferences:modules:screen:list").projectDir = file("library/modules/screen/list")
include(":composepreferences:modules:screen:number")
project(":composepreferences:modules:screen:number").projectDir = file("library/modules/screen/number")

include(":composepreferences:modules:kotpreferences")
project(":composepreferences:modules:kotpreferences").projectDir = file("library/modules/kotpreferences")

include(":demo:android")
include(":demo:windows")

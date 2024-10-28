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

include(":demo:android")
include(":demo:desktop")
